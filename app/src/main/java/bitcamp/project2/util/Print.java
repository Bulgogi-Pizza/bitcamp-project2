package bitcamp.project2.util;

import bitcamp.project2.vo.Todo;
import bitcamp.project2.vo.TodoList;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class Print {
    // ... 기존 코드 ...

    /**
     * 오늘 할 일 목록을 출력하는 메서드
     * @param todoList 출력할 할 일 목록
     */
    public static void printTodayTodoList(TodoList todoList) {
        System.out.println("------ [오늘 할 일]------------------------------");

        // 오늘 날짜를 기준으로 할 일 목록 필터링
        boolean hasTodayTodo = false;
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = sdf.format(today.getTime());

        for (Todo todo : todoList) {
            String todoDateStr = sdf.format(todo.getDeadline().getTime());
            if (todayStr.equals(todoDateStr)) {
                hasTodayTodo = true;
                System.out.printf("- %s, %s, %s, %s, %s, %s\n",
                        todo.getDeadlineDate(),
                        todo.getPriority().getGrade(),
                        todo.getTitle(),
                        todo.getStorage(),
                        todo.getRepeat().stringRepeat(),
                        todo.getTagStringBuilder().toString());
            }
        }

        // 오늘 할 일이 없는 경우 "없음" 출력
        if (!hasTodayTodo) {
            System.out.println("없음");
        }

        System.out.println("------------------------------------------------");
    }


    public static void printTitleBig() {

    }

    public static void printTitle(String title) {
        System.out.println("");
        System.out.println("------<< " + title + " >>------");
    }

    public static void printMenus(String[] menus) {
        for (int i = 0; i < menus.length - 1; i++) {
            System.out.println(i + 1 + ". " + menus[i]);
        }
        System.out.println(
            Ansi.RED.getName() + "0. " + menus[menus.length - 1] + Ansi.INIT.getName());
    }

    public static void printSystem(String str) {
        System.out.println(Ansi.RED.getName() + "[System] " + str + Ansi.INIT.getName());
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

    public static void printTodoListByComplete(TodoList todoList) {
        TodoList todoListDone = new TodoList();
        TodoList todoListNotDone = new TodoList();

        Calendar today = Calendar.getInstance();

        for (int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i);

            if (todo.isComplete()) {
                todoListDone.add(todo);
            } else {
                todoListNotDone.add(todo);
            }
        }

        System.out.println("----------<< 완료한 일 >>----------");
        printTodoList(todoListDone);

        System.out.println("----------<< 미완료한 일 >>----------");
        printTodoList(todoListNotDone);

        System.out.println("");
    }

    public static void printTodoListByPriority(TodoList todoList) {
        TodoList todoListHigh = new TodoList();
        TodoList todoListMiddle = new TodoList();
        TodoList todoListLow = new TodoList();

        for (int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i);

            switch (todo.getPriority().getName()) {
                case 1:
                    todoListLow.add(todo);
                    break;
                case 5:
                    todoListMiddle.add(todo);
                    break;
                case 9:
                    todoListHigh.add(todo);
                    break;
                default:
                    break;
            }
        }

        System.out.println("----------<< 우선순위 상 >>----------");
        printTodoList(todoListHigh);

        System.out.println("----------<< 우선순위 중 >>----------");
        printTodoList(todoListMiddle);

        System.out.println("----------<< 우선순위 하 >>----------");
        printTodoList(todoListLow);

        System.out.println("");
    }

    public static void printTodoListByDate(TodoList todoList) {
        TodoList todoListBefore = new TodoList();
        TodoList todoListAfter = new TodoList();

        Calendar today = Calendar.getInstance();

        for (int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i);

            if (todo.getDeadline().before(today)) {
                todoListBefore.add(todo);
            } else {
                todoListAfter.add(todo);
            }
        }

        System.out.println("----------<< 했어야 할 일 >>----------");
        printTodoList(todoListBefore);

        System.out.println("----------<< 해야 할 일 >>----------");
        printTodoList(todoListAfter);

        System.out.println("");
    }

    public static void printTodoList(TodoList todoList) {

        System.out.println(
            "No | To do |    날짜    | 우선순위 |        제목        |    보관함    |   반복여부   | 태그 ");
        System.out.println(
            "------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i);

            System.out.printf("%02d |  %s  | %s |    %s    |%s|%s|   %s  | %s\n", todo.getNo(),
                todo.getComplete(), todo.getDeadlineDate(),
                todo.getPriority().getGrade(),
                printFittedString(Todo.MAX_LENGTH_TITLE, todo.getTitle()),
                printFittedString(Todo.MAX_LENGTH_STORAGE, todo.getStorage()),
                todo.getRepeat().stringRepeat(),
                todo.getTagStringBuilder().toString());

        }
        System.out.println("");
    }

    public static void printHaveTodoList(TodoList todoList) {

    }

    public static String printFittedString(int length, String str) {
        int strByteLength = str.getBytes().length;
        int strLength = str.length();

        int charNum = (3 * strLength - strByteLength) / 2;
        int korNum = strLength - charNum;
        int space_length = length - charNum - (2 * korNum);
        int space_length2 = space_length;
        if (space_length % 2 == 1) {
            ++space_length2;
        }
        String space = " ";
        StringBuilder fittedString = new StringBuilder();
        for (int i = 0; i < space_length / 2; i++) {
            fittedString.append(space);
        }
        fittedString.append(str);
        for (int i = 0; i < space_length2 / 2; i++) {
            fittedString.append(space);
        }

        return fittedString.toString();
    }
}
