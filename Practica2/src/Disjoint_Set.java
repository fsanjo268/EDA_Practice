//Fernando San Jose Dominguez

import java.util.HashMap;
import java.util.Map;


public class Disjoint_Set {

    // mapa de relaciones
    private Map<Integer, Integer> mapa;

    // mapa de pesos
    private Map<Integer, Integer> mapaPesos;

    // tamaño del mapa
    private int Tamaño;

    /**
     * Constructor vacio
     */
    public Disjoint_Set() {
        mapa = new HashMap<>();
        mapaPesos = new HashMap<>();
        Tamaño = 0;
    }

    /**
     * construyo el Disjoint set con el HashMap por medio de un forEach
     * @param elementos
     */
    public Disjoint_Set( HashMap<Integer, Integer> elementos) {
        mapa = new HashMap<>();
        mapaPesos = new HashMap<>();
        for (Integer key : elementos.keySet()) {
            if (mapa.containsKey(key))
                throw new IllegalArgumentException("los elementos del HashMap contienen dos elementos iguales");
                mapa.put(key, key);
                mapaPesos.put(key, 1);
        }
        Tamaño = elementos.size();
    }

    /**
     * creo un nuevo conjunto con un elemento
     * @param elemento
     * @return true o false segun los contenga
     * @exception java.lang.NullPointerException
     */
    public boolean crearConjunto(Integer elemento) {
        if (elemento == null)
            throw new NullPointerException("El elemento es nulo");
        if (contiene(elemento))
            return false;
        mapa.put(elemento, elemento);
        mapaPesos.put(elemento, 1);
        Tamaño++;
        return true;
    }

    /**
     *devuelve true si no contiene conjuntos
     * @return
     */
    public boolean EstaVacio() {
        if (mapa.isEmpty())
            return true;
        return false;
    }

    /**
     *devuelve el numero de elementos
     * @return map.size().
     */
    public int TamañoElemento() {
        return mapa.size();
    }

    /**
     * devuelve el numero de conjuntos
     * @return setsSize
     */
    public int SetTamañoElemento() {
        return Tamaño;
    }

    /**
     * junta en un solo conjunto los diferentes conjuntos que contengan el elemento 1 y elemento 2, destruyendo los originales
     * @param elemento1
     * @param elemento2
     * @exception java.lang.NullPointerException
     * @exception java.lang.IllegalArgumentException
     */
    public void union(Integer elemento1, Integer elemento2) {
        if ((elemento1 == null) || (elemento2 == null))
            throw new NullPointerException("Uno de los elementos es nulo");
        if ((!contiene(elemento1)) || (!contiene(elemento2)))
            throw new IllegalArgumentException("Illegal Argument.");
            Integer raizElemento1 = encontrar(elemento1);
            Integer raizElemento2 = encontrar(elemento2);
        if (raizElemento1 != raizElemento2) {
            int weightRootItem1 = mapaPesos.get(raizElemento1);
            int weightRootItem2 = mapaPesos.get(raizElemento2);
            if (weightRootItem1 >= weightRootItem2) {
                mapa.put(raizElemento2, raizElemento1);
                mapaPesos.put(raizElemento1, weightRootItem1 + weightRootItem2);
            } else {
                mapa.put(raizElemento1, raizElemento2);
                mapaPesos.put(raizElemento2, weightRootItem1 + weightRootItem2);
            }
            Tamaño--;
        }
    }

    /**
     * Devolver el puntero al raiz del conjunto que contiene el elemento
     * @param elemento
     * @return root
     * @exception java.lang.NullPointerException
     * @exception java.lang.IllegalArgumentException      
     */
    public Integer encontrar(Integer elemento) {
        if (elemento == null)
            throw new NullPointerException("El elemento es nulo");
        if (!contiene(elemento))
            throw new IllegalArgumentException("Illegal Argument.");
            Integer raiz = elemento;
        while (!raiz.equals(mapa.get(raiz)))
            raiz = mapa.get(raiz);
        return raiz;
    }

    /**
     * Devolver true si contiene un item especifico
     * @param elemento
     * @return map.containsKey(item)
     * @exception java.lang.NullPointerException     
     */
    public boolean contiene(Integer elemento) {
        if (elemento == null)
            throw new NullPointerException("El elemento es nulo");
        return mapa.containsKey(elemento);
    }

   public Map<Integer, Integer> getMapa() {
       return mapa;
   }

   public int getTamaño() {
       return Tamaño;

   }public Map<Integer, Integer> getMapaPesos() {
       return mapaPesos;
   }
}