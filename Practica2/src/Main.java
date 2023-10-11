//Fernando San Jose Dominguez

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in =  new Scanner(System.in);
        
        long inicio;
        long fin;
        String fich;
        String extra;
        int tamGrumo;
        int usuario1;
        int usuario2;
        int raiz;
        int usuarios;
        int num =1;
        double porcentaje;

        ArrayList<Relaciones> red;
        Disjoint_Set grumos;
        HashMap<Integer, Integer> ordenarGrumos;
        HashMap<Integer, Integer> seleccionGrumos;
        HashMap<Integer, Integer> nuevasAmistades;

        System.out.println("Analisis de CaraLibro");
        System.out.println("------------------------------------");
        Datos datos = new Datos();//creo el objeto datos

        System.out.print("Inserte nombre fichero: ");
        fich = in.nextLine();
        
//Calcular tiempo de lectura del fichero
        inicio = System.currentTimeMillis();

        red=datos.red(fich);

        fin = System.currentTimeMillis();      
        datos.setLectFich((double)(fin - inicio)/1000);

        System.out.println("Tiempo lectura fichero: "+datos.getLectFich() +" segundos");

//Veo si tengo el fichero
        System.out.print("Fichero de nuevas conexiones (pulse enter si no existe): ");
        extra = in.nextLine();
        
        if(extra.length()!=0){
            
            red = datos.redNueva (red, extra);
        }

        System.out.println("Usuarios "+datos.getUsuarios()+" "+"Conexiones "+datos.getConexiones());

//Insertar tamaño grumo
        System.out.print("Inserte el Porcentaje del tamaño mayor de grumo: ");
        tamGrumo = in.nextInt();
        in.close();

//Calcular tiempo de creacion lista grumos
        inicio = System.currentTimeMillis();

        grumos = datos.listaGrumos(red);
        ordenarGrumos = datos.ordenarGrumos(grumos);
        seleccionGrumos = datos.seleccionGrumos(ordenarGrumos, tamGrumo);
        nuevasAmistades= datos.nuevasAmistades(ordenarGrumos,seleccionGrumos);

        fin = System.currentTimeMillis();
        datos.setCreaListaGru((double)(fin - inicio)/1000);
        
        System.out.println("Tiempo de creacion lista grumos: "+datos.getCreaListaGru() +" segundos");
        System.out.println(datos.getNumGrumos()+" grumos totales");
//Selecciono grumos 
        if(seleccionGrumos.size()!=1){
            System.out.println("Se deben unir los "+seleccionGrumos.size()+" mayores grumos");
            for(Integer grumo : seleccionGrumos.keySet() ){//saco los porcentajes de los mayores grumos
                raiz= grumo;
                usuarios = seleccionGrumos.get(raiz);
                porcentaje = ((double)usuarios/(double)datos.getUsuarios()*100);
                System.out.println("#"+num+": "+usuarios+" ("+porcentaje+" %"+")");
                num++;
                porcentaje =0;
            }
            
 //creo las nuevas a mistades 
            System.out.println("Las nuevas relaciones de amistad son: ");
                for ( Integer grumoPadre : nuevasAmistades.keySet() ) {
                        usuario1= grumoPadre;
                        usuario2 = nuevasAmistades.get(usuario1);
                        System.out.println(usuario1 + " <-> " + usuario2);
                }

        }else{
            System.out.println("El mayor grumo contiene "+datos.getMayorgrumo() + " usuarios "+"(" +datos.getPorcentaje()+")%");
            System.out.println("No son necesarias nuevas relaciones de amistad");
        }
    }
}
