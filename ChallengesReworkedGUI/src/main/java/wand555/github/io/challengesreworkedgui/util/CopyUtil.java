package wand555.github.io.challengesreworkedgui.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wand555.github.io.challengesreworkedgui.rows.Row;

public class CopyUtil {

    public static <E extends Row> ObservableList<E> deepCopy(ObservableList<E> list) {
        return (ObservableList<E>) FXCollections.observableArrayList(list.stream().map(Row::copy).toList());
    }
}
