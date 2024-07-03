package bitcamp.project2.vo;

import java.util.LinkedList;

public class TodoList extends LinkedList<Todo> {

    public static String sortMethod = "Date";

    public Todo getByNo(int no) {
        for (Todo todo : this) {
            if (todo.getNo() == no) {
                return todo;
            }
        }
        return null;
    }

    public int ofIndex(Todo todo) {
        for (int i = 0; i < this.size(); i++) {
            if (todo.equals(this.get(i))) {
                return i;
            }
        }
        return -1;
    }

}
