package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class level9 {
    private static final ArrayList<Quote> quotes = new ArrayList<>();
    private static int num = 1;
    private static final String DIRECTORY = "db/wiseSaying";

    public static void main(String[] args) {
        System.out.println("== 명언 앱 ==");

        // 디렉토리 생성
        new File(DIRECTORY).mkdirs();

        // 기존 파일들 로드
        loadQuotes();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("명령) ");
            String command = sc.nextLine();

            if (command.equals("종료")) {
                break;
            }

            if (command.equals("등록")) {
                System.out.print("명언 : ");
                String content = sc.nextLine();
                System.out.print("작가 : ");
                String author = sc.nextLine();

                Quote quote = new Quote(num, content, author);
                quotes.add(quote);

                // 개별 파일 저장
                saveQuote(quote);

                // lastId.txt 업데이트
                try {
                    Files.write(Paths.get(DIRECTORY + "/lastId.txt"),
                            String.valueOf(num).getBytes());
                } catch (IOException e) {
                    System.out.println("ID 저장 실패");
                }

                System.out.println(num + "번 명언이 등록되었습니다.");
                num++;
            }
            else if (command.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");
                System.out.println("----------------------");
                for (int i = quotes.size() - 1; i >= 0; i--) {
                    Quote q = quotes.get(i);
                    System.out.println(q.id + " / " + q.author + " / " + q.content);
                }
            }
            else if (command.startsWith("삭제?id=")) {
                int id = Integer.parseInt(command.substring(6));
                boolean found = false;

                for (int i = 0; i < quotes.size(); i++) {
                    if (quotes.get(i).id == id) {
                        quotes.remove(i);
                        // 파일 삭제
                        try {
                            Files.delete(Paths.get(DIRECTORY + "/" + id + ".json"));
                        } catch (IOException e) {
                            System.out.println("파일 삭제 실패");
                        }
                        System.out.println(id + "번 명언이 삭제되었습니다.");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                }
            }
            else if (command.startsWith("수정?id=")) {
                int id = Integer.parseInt(command.substring(6));
                boolean found = false;

                for (Quote quote : quotes) {
                    if (quote.id == id) {
                        System.out.println("명언(기존) : " + quote.content);
                        System.out.print("명언 : ");
                        String newContent = sc.nextLine();
                        System.out.println("작가(기존) : " + quote.author);
                        System.out.print("작가 : ");
                        String newAuthor = sc.nextLine();

                        quote.content = newContent;
                        quote.author = newAuthor;

                        // 수정된 명언 파일 저장
                        saveQuote(quote);

                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println(id + "번 명언은 존재하지 않습니다.");
                }
            }
            else if (command.equals("빌드")) {
                JSONArray jsonArray = new JSONArray();
                for (Quote quote : quotes) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", quote.id);
                    jsonObject.put("content", quote.content);
                    jsonObject.put("author", quote.author);
                    jsonArray.put(jsonObject);
                }

                try {
                    Files.write(Paths.get(DIRECTORY + "/data.json"),
                            jsonArray.toString(2).getBytes());
                    System.out.println("data.json 파일의 내용이 갱신되었습니다.");
                } catch (IOException e) {
                    System.out.println("파일 저장 실패");
                }
            }
        }

        sc.close();
    }

    private static void saveQuote(Quote quote) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", quote.id);
        jsonObject.put("content", quote.content);
        jsonObject.put("author", quote.author);

        try {
            Files.write(Paths.get(DIRECTORY + "/" + quote.id + ".json"),
                    jsonObject.toString(2).getBytes());
        } catch (IOException e) {
            System.out.println("파일 저장 실패");
        }
    }

    private static void loadQuotes() {
        File dir = new File(DIRECTORY);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json") && !name.equals("data.json"));

        if (files != null) {
            for (File file : files) {
                try {
                    String content = new String(Files.readAllBytes(file.toPath()));
                    JSONObject json = new JSONObject(content);

                    int id = json.getInt("id");
                    String quoteContent = json.getString("content");
                    String author = json.getString("author");

                    quotes.add(new Quote(id, quoteContent, author));
                    num = Math.max(num, id + 1);
                } catch (IOException e) {
                    System.out.println("파일 로드 실패");
                }
            }
        }
    }
}

class Quote {
    int id;
    String content;
    String author;

    Quote(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }
}