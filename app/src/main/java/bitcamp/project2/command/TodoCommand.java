package bitcamp.project2.command;

import bitcamp.project2.vo.SortByDate;
import bitcamp.project2.vo.SortByPriority;
import bitcamp.project2.vo.Sorter;
import bitcamp.project2.vo.Todo;
import bitcamp.project2.vo.TodoList;

public class TodoCommand {

    public static TodoList todos = new TodoList();

    public static void init() {
        Todo.addStorageList("기본");
    }

    public static void sort() {
        Sorter sorter;

        if (TodoList.sortMethod.equals("Date")) {
            sorter = new SortByDate();
        } else if (TodoList.sortMethod.equals("Priority")) {
            sorter = new SortByPriority();
        } else {
            sorter = new SortByDate();
        }

        todos = sorter.sort(todos);
    }

}
