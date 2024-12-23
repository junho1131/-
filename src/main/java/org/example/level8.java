package org.example;

import java.util.Scanner;

public class level8 {
        public static void main(String[] args) {
                System.out.println("== 명언 앱 ==");

                Scanner sc = new Scanner(System.in);
                String[] quoteList = new String[100]; // 명언을 저장할 배열
                String[] authorList = new String[100]; // 작가를 저장할 배열
                int count = 0; // 현재 등록된 명언 수

                while (true) {
                        System.out.print("명령) ");
                        String command = sc.nextLine();
                        if (command.equals("종료")) {
                                break;
                        }
                        if (command.equals("등록")) {
                                if (count >= quoteList.length) {
                                        System.out.println("더 이상 명언을 등록할 수 없습니다.");
                                        continue;
                                }
                                System.out.print("명언: ");
                                String content = sc.nextLine();
                                quoteList[count] = content; // 배열에 명언 추가
                                System.out.print("작가: ");
                                String author = sc.nextLine();
                                authorList[count] = author; // 배열에 작가 추가
                                System.out.println((count + 1) + "번 명언이 등록되었습니다."); // 현재 등록된 명언 수 출력
                                count++;
                        } else if (command.equals("목록")) {
                                System.out.println("번호 / 작가 / 명언");
                                System.out.println("----------------------");
                                for (int i = count - 1; i >= 0; i--) { // 내림차순으로 출력
                                        if (quoteList[i] != null) { // null 체크
                                                System.out.println((i + 1) + " / " + authorList[i] + " / " + quoteList[i]);
                                        }
                                }
                        } else if (command.startsWith("삭제?id=")) {
                                // 삭제 명령어 처리
                                String idString = command.substring(6); // "삭제?id=" 이후의 문자열 추출
                                int id;
                                try {
                                        id = Integer.parseInt(idString) - 1; // 입력된 ID를 정수로 변환하고 0-based 인덱스로 변환
                                        if (id < 0 || id >= count || quoteList[id] == null) {
                                                System.out.println((id + 1) + "번 명언은 존재하지 않습니다."); // 수정된 출력
                                        } else {
                                                // 명언 삭제
                                                quoteList[id] = null; // 해당 인덱스를 null로 설정
                                                authorList[id] = null; // 해당 인덱스를 null로 설정
                                                System.out.println((id + 1) + "번 명언이 삭제되었습니다.");
                                        }
                                } catch (NumberFormatException e) {
                                        System.out.println("잘못된 ID입니다.");
                                }
                        } else if (command.startsWith("수정?id=")) {
                                // 수정 명령어 처리
                                String idString = command.substring(6); // "수정?id=" 이후의 문자열 추출
                                int id;
                                try {
                                        id = Integer.parseInt(idString) - 1; // 입력된 ID를 정수로 변환하고 0-based 인덱스로 변환
                                        if (id < 0 || id >= count || quoteList[id] == null) {
                                                System.out.println((id + 1) + "번 명언은 존재하지 않습니다."); // 수정된 출력
                                        } else {
                                                // 기존 명언 및 작가 출력
                                                System.out.println("명언(기존) : " + quoteList[id]);
                                                System.out.print("명언 : ");
                                                String newContent = sc.nextLine(); // 새로운 명언 입력
                                                quoteList[id] = newContent; // 명언 수정

                                                // 기존 작가 출력
                                                System.out.println("작가(기존) : " + authorList[id]);
                                                System.out.print("작가 : ");
                                                String newAuthor = sc.nextLine(); // 새로운 작가 입력
                                                authorList[id] = newAuthor; // 작가 수정


                                        }
                                } catch (NumberFormatException e) {
                                        System.out.println("잘못된 ID입니다.");
                                }
                        } else {
                                System.out.println("알 수 없는 명령입니다.");
                        }
                }

                sc.close(); // Scanner 닫기
        }
}
