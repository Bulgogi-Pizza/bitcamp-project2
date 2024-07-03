package bitcamp.project2.util;

import bitcamp.project2.vo.Todo;
import bitcamp.project2.vo.TodoList;
import java.time.LocalDate;
import java.util.Calendar;

public class Print {

    public static void printTitleBig() {

    }

    public static void printTitle(String title) {
        System.out.println("");
        System.out.println("------<< " + title + " >>------");
    }

    public static void printMenus(String[] menus) {
        for (int i = 0; i < menus.length; i++) {
            System.out.println(i + 1 + ". " + menus[i]);
        }
    }

    public static void printSystem(String str) {
        System.out.println(Ansi.RED + "[System] " + str + Ansi.INIT);
    }

    public static Calendar printCalendar(int year, int month) {
        // 현재 연도와 월 가져오기
        String boldAnsi = "\033[1m";
        String redAnsi = "\033[31m";
        String resetAnsi = "\033[0m";
        LocalDate today = LocalDate.now();

        Calendar calendar = Calendar.getInstance();
        System.out.println("---------------------");

        // 해당 월의 첫 번째 날로 설정
        calendar.set(year, month, 1);

        // 해당 월의 마지막 일 가져오기
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 달력 출력
        System.out.printf("%s%d년 %d월%s \n", boldAnsi, year, (month + 1), resetAnsi);
        System.out.println("일 월 화 수 목 금 토");

        // 해당 월의 첫 번째 날의 요일을 가져오기
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // 달력 출력용 변수 설정
        int dayOfWeekCounter = firstDayOfWeek - 1; // Adjust to zero-based index

        // 첫 번째 주 앞쪽 공백 출력
        for (int i = 0; i < dayOfWeekCounter; i++) {
            System.out.print("   ");
        }

        // 달력 일 출력
        for (int day = 1; day <= daysInMonth; day++) {
            dayOfWeekCounter++;
            if (today.getDayOfMonth() == day && today.getMonthValue() == month + 1
                && today.getYear() == year) {
                System.out.printf("%s%2d%s ", (boldAnsi + redAnsi), day, resetAnsi);
            } else {
                System.out.printf("%2d ", day);
            }
            if (dayOfWeekCounter == 7) {
                dayOfWeekCounter = 0;
                System.out.println();
            }
        }
        // 마지막 주 줄바꿈
        if (dayOfWeekCounter != 0) {
            System.out.println();
        }
        System.out.println("---------------------");
        return calendar;
    }

    public static void printTodoList(TodoList todoList) {
        printTitleBig();

        System.out.println(
            "No | To do |    날짜    | 우선순위 |        제목        |    보관함    |   반복여부   | 태그 ");
        System.out.println(
            "------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i);

            System.out.printf("%02d |  %s  | %s |    %s    |%s|%s| %s | %s\n", todo.getNo(),
                todo.getComplete(), todo.getDeadlineDate(),
                todo.getPriority().getName(),
                printFittedString(Todo.MAX_LENGTH_TITLE, todo.getTitle()),
                printFittedString(Todo.MAX_LENGTH_STORAGE, todo.getStorage()),
                todo.getRepeat().stringRepeat(),
                todo.getTagList().toString());

        }
        System.out.println("");
    }

    public static void printMainTodoList(TodoList todoList) {
        printTitleBig();

        Print.printTitle(" 목표");
        // print 오늘 && 미완료 목표

        Print.printTitle("미완료 목표");
        // print after 오늘 && 미완료 목표

        Print.printTitle("완료 목표");


    }

    public static void printHaveTodoList(TodoList todoList) {

    }

    public static String printFittedString(int length, String str) {
        int strByteLength = str.getBytes().length;
        int strLength = str.length();

        int charNum = (3 * strLength - strByteLength) / 2;
        int korNum = strLength - charNum;
        int space_length = length - charNum - (2 * korNum);
        if (space_length % 2 == 1) {
            ++space_length;
        }
        String space = " ";
        StringBuilder fittedString = new StringBuilder();
        for (int i = 0; i < space_length / 2; i++) {
            fittedString.append(space);
        }
        fittedString.append(str);
        for (int i = 0; i < space_length / 2; i++) {
            fittedString.append(space);
        }

        return fittedString.toString();
    }
}
