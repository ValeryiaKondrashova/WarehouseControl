package mwarehouse.warehouse.client;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition tt;

    public Shake(Node node){  //Node - это любой объект на нашем окне (кнопка, текстовое поле, надпись и тд). Такой объект мы и будем передавать в этот конструктор, то есть какой объект будем трясти
        tt = new TranslateTransition(Duration.millis(70), node); //параметры: сколько анимировать и какой объект
        tt.setFromX(0f); // отступ от Х
        tt.setByX(10f);  // насколько он передвинется относительно Х
        tt.setCycleCount(4); //сколько раз анимация
        tt.setAutoReverse(true); //если мы делаем анимацию и происходит движение объекта, то тут указываем true, чтобы объект вернулся на исходную позицию
        // Если true, то Animation будет двигаться вперед по первому циклу, затем разворачивается по второму циклу, и так далее.
        tt.setFromX(0f);
        tt.setByX(10f);

    }

    public void playAnim(){  //метод типа void который запускает анимацию
        tt.playFromStart();
    }

}