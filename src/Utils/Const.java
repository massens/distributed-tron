package Utils;

public interface Const {
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int NOACTION = 5;
    public static final int CLOSE = 50;

    
    public static final int SCREENX = 700;
    public static final int SCREENY = 700;
    public static final int PATHSIZE = 10;


    public static final int STEP = 1;
    public static final int TASKPERIOD = 50;
    
    public static final int[] finishCode= new int[] {-1,-1,-1,-1};


    
    //Accions en NotificaObservadors
      public static final int UPDATE_POSITION = 0;
      public static final int ACABA_PARTIDA = 1;

}
