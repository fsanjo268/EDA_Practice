//Fernando San Jose Dominguez

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Datos{
    private int usuarios;
    private int conexiones;
    private int numGrumos;
    private int mayorGrumo;
    private double porcentaje;
    private double lectFich;
    private double creaListaGru;
    
    

//creo gets
    public int getUsuarios() {
        return usuarios;
    }

    public int getConexiones() {
        return conexiones;
    }

    public int getNumGrumos(){
        return numGrumos;
    }

    public int getMayorgrumo(){
        return mayorGrumo;
    }

    public double getPorcentaje(){
        return porcentaje;
    }

    public double getLectFich() {
        return lectFich;
    }

    public double getCreaListaGru() {
        return creaListaGru;
    }

  
//creo sets
    public void setUsuarios(int usuarios) {
        this.usuarios = usuarios;
    }

    public void setConexiones(int conexiones) {
        this.conexiones = conexiones;
    }

    public void setNumGrumos(int numGrumos){
        this.numGrumos = numGrumos;
    }

    public void setMayorGrumo(int mayorGrumo){
        this.mayorGrumo = mayorGrumo;
    }

    public void setPorcentaje(double porcentaje){
        this.porcentaje = porcentaje;
    }

    public void setLectFich(double lectFich) {
        this.lectFich = lectFich;
    }
    
    public void setCreaListaGru(double creaListaGru) {
        this.creaListaGru = creaListaGru;
    }



//creo los metodos

/**
 * Creo el Arraylist de conexiones (fichero debe encontrarse en la ruta "src/")
 * @param fichero
 * @return redes
 * @throws IOException
 */
public  ArrayList<Relaciones> red (String fichero) throws IOException {

    Relaciones relaciones;
    ArrayList<Relaciones> redes = new ArrayList<>();
    
    File archivo;
    FileReader readFich;
    BufferedReader buffer;
    String line;
    int cont=0;

    try{
        archivo =new File("src/"+fichero);
        readFich = new FileReader(archivo);
        buffer = new BufferedReader(readFich); 
        while((line=buffer.readLine())!=null){//leo las lineas del fichero
           if(cont == 0){
               setUsuarios(Integer.parseInt(line));//guardo en datos  los usuarios
            }else if (cont == 1){
                setConexiones(Integer.parseInt(line));//guardo en datos conexiones
            }else{
                String[]amigos = line.split(" ");//Guardar las amistadades
                String amigo1 = amigos[0];
                String amigo2 = amigos[1];
                relaciones = new Relaciones(Integer.parseInt(amigo1),Integer.parseInt(amigo2));;
                redes.add(relaciones);;//hacer una arrayList de amigos que a su vez esa posicion se almacene en otra arrylist
            }
           
           cont++;
        }
    } catch(FileNotFoundException e){
        System.out.println("No se encontró el archivo");
    }

    return redes;
}

/**
 * Creo el Arraylist de conexiones de fichero extra
 * @param red
 * @param extra
 * @return red
 * @throws IOException
 */
public  ArrayList<Relaciones> redNueva (ArrayList<Relaciones> red, String extra) throws IOException {
    Relaciones relaciones;  
    File archivo;
    FileReader readFich;
    BufferedReader buffer;
    String line;

    try{
        archivo =new File(extra);
        readFich = new FileReader(archivo);
        buffer = new BufferedReader(readFich); 
        while((line=buffer.readLine())!=null){//leo el fichero
          
            String[]amigos = line.split(" ");//Guardar las amistadades
            String amigo1 = amigos[0];
            String amigo2 = amigos[1];
            relaciones = new Relaciones(Integer.parseInt(amigo1),Integer.parseInt(amigo2));
            red.add(relaciones);//hacer una arrayList de amigos que a su vez esa posicion se almacene en otra arrylist
        }
    } catch(FileNotFoundException e){
        System.out.println("No se encontró el archivo");
    }

    return red;
}

/**
 * Creo el ArrayList de usuarios unicos de la red y los grumos
 * @param red
 * @return grumos
 */
public Disjoint_Set listaGrumos (ArrayList<Relaciones> red){
        
    HashMap<Integer, Integer> usuarios = new HashMap<>();
    
    for(int i = 0; i<red.size();i++){//leo el arraylsit red
        if(!usuarios.containsKey(red.get(i).getUsuario1())){
            usuarios.put(red.get(i).getUsuario1(),null);
        }
        if(!usuarios.containsKey(red.get(i).getUsuario2())){
            usuarios.put(red.get(i).getUsuario2(),null);
        }
    }

    Disjoint_Set grumos = new Disjoint_Set(usuarios); //creo el Disjoint Set
    for(int i =0;i<red.size();i++){
        grumos.union(red.get(i).getUsuario1(), red.get(i).getUsuario2());
   }
    setNumGrumos(grumos.getTamaño());
    return grumos;
}

/**
 * Ordeno los grumos de mayor a menor
 * @param listaGrumos
 * @return mapaPesosOrdenado
 */
public HashMap<Integer, Integer> ordenarGrumos (Disjoint_Set listaGrumos){
    
    Map<Integer, Integer> mapaPesosDesorden = listaGrumos.getMapaPesos();
         
    LinkedHashMap<Integer, Integer> mapaPesosOrdenado = new LinkedHashMap<>();
 
    mapaPesosDesorden.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())) //Uso Comparator.reverseOrder() para hacer el orden inverso
    .forEachOrdered(x -> mapaPesosOrdenado.put(x.getKey(), x.getValue()));
 
    return mapaPesosOrdenado;
}

/**
 * Selecciono los grumos que sumen el pricentaje pedido para un unico grumo comun
 * @param grumosOrdenados
 * @param tamGrumo
 * @return seleccion
 */
public HashMap<Integer, Integer> seleccionGrumos( HashMap<Integer, Integer> grumosOrdenados, int tamGrumo){

    LinkedHashMap<Integer, Integer> seleccion = new LinkedHashMap<>();
    double porcentajeGrumo=0;
    int usrTotales=0;
    int padre=0;
    int usuarios=0;
    boolean flag = true;

    for ( Integer grumoPadre : grumosOrdenados.keySet() ) {
        padre = grumoPadre;
        usuarios = grumosOrdenados.get(padre);
        
        porcentajeGrumo=((double)usuarios/(double)getUsuarios()*100);//calculo el %
        porcentaje=porcentaje+porcentajeGrumo;

        if(porcentaje<tamGrumo){//veo el porcentaje de la posicion si es > al parametro
            seleccion.put(padre, usuarios);
            usrTotales= usrTotales + usuarios;
            
        }else if(porcentaje>=tamGrumo && flag == true){//si no es le sumo el al total y avanzo al siguiente
            flag = false;
            seleccion.put(padre, usuarios);
            usrTotales= usrTotales + usuarios;
            break;
        }
        porcentajeGrumo=0;
        padre = 0;
        usuarios =0;
    }
    setMayorGrumo(usrTotales);
    setPorcentaje(porcentaje);
    return  seleccion;
}

/**
 * Creo las nuevas amistades del grumo en comun
 * @param grumosOrdenados
 * @param seleccionGrumos
 * @return nuevaAmistad
 */
public HashMap<Integer, Integer> nuevasAmistades(HashMap<Integer, Integer> grumosOrdenados,HashMap<Integer, Integer> seleccionGrumos){

    LinkedHashMap<Integer, Integer> nuevaAmistad= new LinkedHashMap<>();
    int padre1 = 0;
    int padre2 =0;
    int cont = 0;

    FileWriter fichero = null;
    PrintWriter pw = null;

    try
    {
        fichero = new FileWriter("extra.txt");//creamos el fichero
        pw = new PrintWriter(fichero);


        for ( Integer grumoPadre : seleccionGrumos.keySet() ) {
            padre2 = grumoPadre;
            if(cont <1){
                padre1 = padre2;//guardo el amigo 2 como amigo 1 sin almacenar en HashMap
                
            }if(cont>0){
                nuevaAmistad.put(padre1, padre2);
                pw.println(padre1+" "+padre2);
                padre1=padre2;//guardo el amigo 2 como amigo 1
            }
            cont++;
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
       try {// Nuevamente aprovechamos el finally para asegurarnos que se cierra el fichero.
       if (null != fichero)
          fichero.close();
        } catch (Exception e2) {
          e2.printStackTrace();
        }
    }
    return nuevaAmistad;
}

}