//Fernando San Jose Dominguez

public class Relaciones {

    private int usuario_1;
    private int usuario_2;

    public Relaciones (int usuario_1, int usuario_2){
        this.usuario_1=usuario_1;
        this.usuario_2=usuario_2;
    }

    public int getUsuario1(){
        return usuario_1;
    }
    public int getUsuario2(){
        return usuario_2;
    }

    public void setUsuario1(int usuario_1){
        this.usuario_1 = usuario_1; 
    }
    public void setUsuario2(int usuario_2){
        this.usuario_2=usuario_2;
    }
}
