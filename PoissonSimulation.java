import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


//Jesús Eduardo Nava Castan 174489
//Juan Francisco Cruz Baquedano 176243

public class PoissonSimulation {

    private static final double LAMBDA = 6;
    private static final int NUMERO_DE_CAJAS = 4;


    public static int poissonRandom(double lambda) {
        int k = 0;
        double p = 1.0;
        double expLambda = Math.exp(-lambda);

        do {
            k++;
            p *= Math.random();
        } while (p >= expLambda);

        return k - 1;
    }


    //Función para asignar a los clientes a las cajas disponibles en función del tiempo de atención actual de cada caja
    public static void atenderClientesEnCajas(Queue<Integer> fila, int[] clientesAtendidosPorCaja)
    {
        int[] tiemposAtencion = new int[NUMERO_DE_CAJAS];
        Random random = new Random();

        while (!fila.isEmpty())
        {
            // Buscar la caja con el tiempo de atención más corto
            int cajaMasRapida = encontrarCajaMasRapida(tiemposAtencion);

            if (cajaMasRapida != -1)
            {
                // Si se encuentra una caja disponible, asignar el cliente
                Integer clienteAtendido = fila.poll();
                int tiempoAtencion = 1 + random.nextInt(10); // Tiempo de atención aleatorio entre 1 y 10 segundos para todas las cajas

                System.out.println("\nCliente " + clienteAtendido + " está siendo atendido en la caja " + (cajaMasRapida + 1));
                System.out.println("Tiempo de atención: " + tiempoAtencion + " segundos");
                tiemposAtencion[cajaMasRapida] += tiempoAtencion;

                // Actualizar el registro de clientes atendidos por esta caja
                clientesAtendidosPorCaja[cajaMasRapida]++;
            } //end if

            // Si no hay cajas disponibles el cliente tiene que seguir esperando


        }//end while
    }//end atenderClientesEnCajas

    
    //función para poder encontrar la disponibilidad de las cajas con respecto al tiempo de atención
    public static int encontrarCajaMasRapida(int[] tiemposAtencion) 
    {
        int cajaMasRapida = -1;
        int tiempoMasCorto = Integer.MAX_VALUE;

        for (int i = 0; i < NUMERO_DE_CAJAS; i++)
        {
            if (tiemposAtencion[i] < tiempoMasCorto)
            {
                tiempoMasCorto = tiemposAtencion[i];
                cajaMasRapida = i;
            }//end if
        }//end for

        return cajaMasRapida;
    }//end encontrarCajaMasRapida

    public static void main(String[] args) 
    {
        Queue<Integer> fila = new LinkedList<>(); // Usamos una cola para la fila de espera
        int[] clientesAtendidosPorCaja = new int[NUMERO_DE_CAJAS]; // Array para almacenar clientes atendidos por cada caja

        for (int i = 0; i < 8; i++) 
        { // Simulamos 8 horas
            int arrivals = poissonRandom(LAMBDA);
            System.out.println("\nEn la hora " + (i + 1) + ", llegaron " + arrivals + " clientes.");
            for (int j = 1; j <= arrivals; j++)
            {
                fila.add(j);
            }//end for

            System.out.println("La fila es: " + fila);
            atenderClientesEnCajas(fila, clientesAtendidosPorCaja);
        }//end for

        // Imprimir el registro de clientes atendidos por cada caja
        for (int i = 0; i < NUMERO_DE_CAJAS; i++) 
        {
            System.out.println("Caja " + (i + 1) + " atendió a " + clientesAtendidosPorCaja[i] + " clientes.");
        }//end for
    }//end main

}//PoissonSimulation
