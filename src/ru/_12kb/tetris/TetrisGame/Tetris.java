package ru._12kb.tetris.TetrisGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Runnable;
import ru._12kb.mesagelib.*;

/*
Стакан представляет собой поле, разбитое на ячейки. Каждая ячейка может быть
в 2ух состояниях - закрашена, пуста.
Для обозначения ячейки используются её координаты.

Контейнер body это и есть стакан. В нем хранятся координаты закрашенных ячеек,
в виде массивов Integer размерности 2.
0й элемент каждого массива - координата ячейки по горизонтали.
1й - по вертикали.
body - последовательный контейнер.

Когда приходит новая фигура, она ложится в конец массива стакана. Затем, ссылки
на добавленные элементы отдаются переменной fig. Т.е. одни и те же элементы теперь
лежат в body и в то же время есть в fig. Сделано это только из соображений удобства.
Ничто не мешает хранить вместо этого целочисленную переменную с количеством переменных
в конце body и использовать её для перемещений фигуры.

Перемещения фигуры сделаны просто инкрементированием соответствующей переменной в контейнере фигуры.
Функции перемещения: step...

Функции try... сделаны для проверки корректности перемещений.

Когда фигура упала контейнер fig просто переводится в null. Элементы остаются лежать в массиве body.

*/

/*
TODO перенести фигуру в отдельный объект
вынести код

*/

public class Tetris implements java.lang.Runnable, Listener {
    //а это мой друг - Стакаша
    //каждая ячейка = закрашенный квадратец
    protected ArrayList<Integer[]> body;
  
    // размерные параметры. Можно менять размеры стакана, размеры ячейки в пикселях.
    protected final byte bodySizeX = 15, bodySizeY = 50; // Стакашкины размеры в ячейках.
    protected final byte cellSize = 10; //ячейкины размеры. В пикселях.
    
    // собственно фигура, и точка её вращения.
    protected List<Integer[]> fig;  // сцыль на текущую фигурку (подмассив Стакадия)
    protected Integer[] rotationPoint;
    
    //командный интерфейс, для общения с графикой и контроллером.
    protected Speaker comandor;
    
    /*фигуры. 0й элемент массива - точка вращения. Не добавляется в body. Вспоминаем про неё только при
    вращении. Больше нигде она не используется. Не обязательно закрашена.

    1й точкой должна быть всегда точка вставки - (0,0). Это позволяет не лазить в исходный массив фигуры
    при вращении. Можно было учесть это в коде, но так нагляднее и проще. Точка с координатами (0,0) всегда
    закрашена. Если это не так, нужно сдвинуть систему координат, чтобы это условие выполнялось.

    Остальные точки - координаты закрашенных точек по отношению к точке вставки.*/
    final static int [][] L = {{0,0},{0,0},{1,0},{0,1},{0,2}};
    final static int [][] Lr = {{0,0},{0,0},{-1,0},{0,1},{0,2}};
    final static int [][] BOX = {{2,2},{0,0},{1,0},{0,1},{1,1}};
    final static int [][] I = {{1,4},{0,0},{0,1},{0,2},{0,3}};
    final static int [][] sBOX = {{0,1},{0,0},{0,1},{1,1},{-1,0}};
    final static int [][] sBOXr = {{0,1},{0,0},{0,1},{1,0},{-1,1}};
    final static int [][] T = {{0,0},{0,0},{0,1},{1,0},{-1,0}};
    
    
    public Tetris(){
        body = new ArrayList();
        String [] cmds = {"up", "down", "left", "right", "drop", "pause", "resume",
         n}
        comandor = new Speaker()
    }
        
    // комментарии излишни
    public void run() {
        // главный цикл
             for (;;){
                // позиция вставки фигуры - по центру
                Integer [] a = new Integer[2];
                a[0]=bodySizeX/2;
                a[1]=0;
                
                // выбор фигуры. В функцию засылать все фигуры что учавствуют
                int [][] figure = selectFigure(L,BOX,I,sBOX,Lr,sBOXr,T);
                
                // вставка фигуры и проверка на геймовер
                insertFig(figure, a);
                if (!AllesInOrnung()){
                    return;
                }
                
                // катим фигуру вниз
                for(;;){
                    try {
                        Thread.sleep(200);
                    } catch (java.lang.InterruptedException e){
                        
                    }
                    if (!tryDown()){
                        break;
                    }
                }
                // фигура прикачена. Забываем её.
                forgetFigure();
                checkLines();
            }
    }
    
    // рандомный выбор фигуры из предложенных
    static int [][] selectFigure(int [][] ... figures){
        Random r = new Random();
        int a = r.nextInt(figures.length);
        return figures[a];
    }
    
    // вставка фигуры в стакан, а так же регистрация и прописка...
    protected void insertFig(int [][] newFig, Integer [] coords){
        if (fig != null){
            System.out.println("Ошибка. Нельзя вставить фигуру, т.к. еще не прошла предыдущая.");
        }
        for(int i=1; i<newFig.length; i++){
            body.add(ArrayOperations.sum(ArrayOperations.toInteger(newFig[i]), coords));
        }
        fig = body.subList(body.size()-(newFig.length-1),body.size());
        rotationPoint = ArrayOperations.toInteger(newFig[0]);
    }
    
    // удаление фигуры. Более не используется.
    protected void deleteFig(){
        fig.removeAll(fig);
        fig = null;
    }
            
    
    protected void stepDown(){
        for(int i=0; i<fig.size();i++){
            fig.get(i)[1]+=1;
        }
    }
    
    protected void stepUp(){
        for(int i=0; i<fig.size();i++){
            fig.get(i)[1]-=1;
        }
    }
            
    protected void stepRight(){
        for(int i=0; i<fig.size();i++){
            fig.get(i)[0]+=1;
        }
    }
    
    protected void stepLeft(){
        for(int i=0; i<fig.size();i++){
            fig.get(i)[0]-=1;
        }
    }
    

    protected void rotateRight(){
        
        Integer [] basePoint = fig.get(0).clone();
        
        rotationPoint[0]=0;
        rotationPoint[1]=0;
        
        for (int i=0; i<fig.size(); i++){
            
            Integer [] n = fig.get(i);
            
            n[0]-=basePoint[0]+rotationPoint[0];
            n[1]-=basePoint[1]+rotationPoint[1];
            
            
            n[0]+=n[1];
            n[1]-=n[0];
            n[0]+=n[1];
            
            n[0]+=basePoint[0]+rotationPoint[0];
            n[1]+=basePoint[1]+rotationPoint[1];
        }
        
        
    }
    

    protected void rotateLeft(){
        rotateRight();rotateRight();rotateRight();
    }

    protected boolean tryDown(){
        stepDown();
        if (!AllesInOrnung()){
            stepUp();
            return false;
        }
        return true;
    }
    
    protected boolean tryRight(){
        stepRight();
        if (!AllesInOrnung()){
            stepLeft();
            return false;
        }
        return true;
    }
    
    protected boolean tryLeft(){
        stepLeft();
        if (!AllesInOrnung()){
            stepRight();
            return false;
        }
        return true;
    }
    
    protected boolean tryRotateRight(){
        rotateRight();
        if (!AllesInOrnung()){
            rotateLeft();
            return false;
        }
        return true;
    }
    
    // проверка - нет ли у нас одинаковых точек в body. Делается после перемещений,
    // читобы отслеживать их корректность.
    protected  ArrayList<Integer[]> checkFigureIntersections(){
        
        ArrayList<Integer[]> result = new ArrayList<>();
        
        for (int i=0; i < body.size() - fig.size(); i++){
            Integer [] n = body.get(i);
            for (int j=0; j<fig.size(); j++){
                if (ArrayOperations.ArraysEquals(n, fig.get(j))){
                    result.add(n);
                }
            }
        }
        return result;
    }
    
    // проверка заполненных рядов
    protected void checkLines(){
       int [] lines = new int[bodySizeY];
       
       for (int i=0; i<body.size(); i++){
           lines[body.get(i)[1]]+=1;
       }
       
       for (int i=0; i<lines.length; i++){
           if (lines[i]==bodySizeX){
               removeLine(i);
           }
       }
        
    }
    
    // удаление всех точек указанного ряда
    protected void removeLine(int y){
        // нельзя использовать если присутствует фигура.
        
        int lineToRemove = y;
        
        for (int i=0; i<body.size(); i++){
            if (body.get(i)[1]==lineToRemove){
                body.remove(i--);
            }
        }
        
        for (int i=0; i<body.size(); i++){
           if (body.get(i)[1] < lineToRemove){
               Integer [] n = body.get(i);
               n[1]+=1;
               body.set(i, n);
           }
        }
    }
    
    // проверка - не вылазит ли фигура за пределы стакана
    protected ArrayList<Integer[]> checkFigureOutOfBound(){
        
        ArrayList<Integer[]> result = new ArrayList<>();
        
        for (Integer [] i:fig){
            if ((i[0] < 0) || (i[0] >= bodySizeX)){
                result.add(i);
                continue;
            }
            if ((i[1] < 0) || (i[1] >= bodySizeY)){
                result.add(i);
            }
        }
        return result;
    }
    
    // общая проверка. Сделана для удобства.
    protected boolean AllesInOrnung(){
        return checkFigureIntersections().isEmpty() && checkFigureOutOfBound().isEmpty();
    }
    
    // разрегистрация фигуры. Нужна после падения.
    protected void forgetFigure(){
        fig = null;
    }
  
    @Override
    public void react(String word){
        if (word == null){
            System.err.println("Ошибка. Tetris.react(): пустая ссылка передана в качестве аргумента");
        }
    
        switch(word){
            case "up":
                this.tryRotateRight();
                break;
            case "down":
                this.tryDown();
                break;
            case "left":
                this.tryLeft();
            case "right":
                this.tryRight();
            case "drop":
                while (this.tryDown()){};
            default :
                
        }
        
    }
    
    // Все, что хотели узнать но боялись спросить про отрисовку.
/*
    @Override
    public void paint (Graphics c){
        
        int frameSizePixelsX = cellSize*bodySizeX+31;
        int frameSizePixelsY = cellSize*bodySizeY+30+30;
        buffer = new BufferedImage(frameSizePixelsX,frameSizePixelsY ,java.awt.image.BufferedImage.TYPE_INT_BGR);
        
        Graphics g = buffer.getGraphics();
        
        int basePointX = 15;
        int basePointY = 30+10;
        g.drawRect(basePointX-1,basePointY-1,cellSize*bodySizeX+1,cellSize*bodySizeY+1);
        
        // отрисовка Стакашки
        for (Integer [] i:body){
            int x = basePointX + i[0]* cellSize;
            int y = basePointY + i[1]* cellSize;
            
            g.fillRect(x+1,y+1,cellSize-1,cellSize-1);
        }
        
        c.drawImage(buffer,0,0,null);
    }
*/
    // велосипед. Набор функций для работы с массивами. Класс java.util.Arrays? Не, не слышал.
        static class ArrayOperations {
            static Integer [] toInteger(int[] arr){
                Integer [] result = new Integer[arr.length];
                for(int i=0;i<arr.length;i++){
                    result[i]=arr[i];
                }
                return result;
            }


            static Integer[] sum(Integer[] a, Integer [] b){
                int length = (a.length < b.length) ? a.length : b.length;
                Integer [] result = new Integer [length];
                for (int i=0; i<length; i++){
                    result[i]=a[i]+b[i];
                }
                return result;
            }
            
            static Integer [] reverse(Integer [] a){
                Integer [] result = new Integer [a.length];
                
                for (int i=0; i < a.length; i++){
                    result[i] = -a[i];
                }
                return result;
            }
            
            static boolean ArraysEquals(Integer [] a, Integer [] b){
                if (a.length != b.length) return false;
                for (int i=0; i<a.length; i++){
                    if (a[i]!=b[i])return false;
                }
                return true;
            }
            
            static void add (Integer[] a, Integer [] b){
                
                for (int i=0; i<a.length; i++){
                    a[i]=a[i]+b[i];
                }
            }
            
        }
}

// не учтено: 
// -нет сообщения Дерьмовер. При переполнении стакана тупо выход.
// -нет очков. Без очков - грустно.
// -не настраивается скорость падения фыгур. Задается в коде жестко.
// -вращается кубик. В общем случае нет способа задать невозможность вращения фигуры.
// -небольшое количество фигур. Извращенная фантазия тетрисописателей придумала куда больше...
// -нет возможности изменять вероятность выпадения той или иной фигуры. А если охото чтобы палка падала
//в 2 раза чаще? :).