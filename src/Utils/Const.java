package Utils;

public interface Const {
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int SPACEBAR = 4;
    public static final int NOACTION = 5;
    public static final int BOMBER = 6;
    public static final int NORMAL = 7;
    public static final int JUMPER = 8;
    public static final int CLOSE = 50;

    
    public static final int SCREENX = 700;
    public static final int SCREENY = 600;
    
    public static final int PATHSIZE = 5;  
    public static final int STEP = 5;
    public static final int TASKPERIOD = 50;

    public static final int RADI_BOMBA = 6;
    public static final int JUMP_LENGTH = 30;
    
    //Accions en NotificaObservadors
    public static final int UPDATE_POSITION = 0;
    public static final int ACABA_PARTIDA = 1;
    public static final int ENVIA_PUNTIACIONS = -2;
    public static final int[] FINISH_CODE = new int[] {-1,-1,-1,-1};

    public static final int ENVIA_BOMBA = 2;
    
    //No s'hauria de canviar el numero de jugadors
    public static final int NUM_JUGADORS = 2;

}
