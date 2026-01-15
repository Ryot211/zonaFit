package ryot.zona_fit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ryot.zona_fit.modelo.Cliente;
import ryot.zona_fit.servicio.IClienteServicio;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {
    @Autowired
    private IClienteServicio clienteServicio;

    private static final Logger logger =
            LoggerFactory.getLogger(ZonaFitApplication.class);

    String nl = System.lineSeparator();

	public static void main(String[] args) {

        logger.info("Inciando la aplicacion");
        // Levantar la fabrica de Spring
        SpringApplication.run(ZonaFitApplication.class, args);
        logger.info("Aplicacion finalizada");
	}

    @Override
    public void run(String... args) throws Exception {
        ZonaFitApp();
    }
    private void ZonaFitApp(){
        logger.info("*** Aplicacion Zona Fit ***" + nl);
        var salir = false;
        var consola = new Scanner(System.in);
        while(!salir){
            var opcion = mostraMenu(consola);
          salir= ejecutarOpciones(consola,opcion);
            logger.info(nl);

        }
    }
    private int mostraMenu(Scanner consola){
        logger.info("""
        \n*** Aplicacion Zona Fit (GYM) ***
                1. Listar Clientes
                2. Buscar Cliente
                3. Agregar Cliente
                $. Modificar Cliente
                5. Eliminar Cliente
                6. Salir
                Elige una opcion:\s""");
        return  Integer.parseInt(consola.nextLine());
    }
    private boolean ejecutarOpciones(Scanner consola, int opcion){
        var salir = false;
        switch (opcion){
            case 1 ->{
                logger.info(nl+ "----- Listado de Clientes: -----"+nl);
                List<Cliente> clientes = clienteServicio.listarClientes();
                clientes.forEach(cliente -> logger.info(cliente.toString()+nl));
            }
            case 2 ->{
               logger.info(nl+ "----- Buscar Cliente: -----"+nl);
               logger.info(nl+"Ingresa el id del cliente que deseas buscar : ");
               var idCliente = Integer.parseInt(consola.nextLine());
               Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
               logger.info("El cliente es : "+cliente.toString());
            }
            case 3 ->{
                logger.info(nl+ "----- Agregar Cliente: -----"+nl);
                logger.info(nl+"Ingresa el nombre del Cliente nuevo :");
                var nombre = consola.nextLine();
                logger.info(nl+"Ingresa el apellido de Cliente nuevo :");
                var apellido = consola.nextLine();
                logger.info(nl+"Ingresa la membresia del Cliente nuevo :");
                var membresia = Integer.parseInt(consola.nextLine());
                var cliente = new Cliente();
                cliente.setNombre(nombre);
                cliente.setApellido(apellido);
                cliente.setMembresia(membresia);
                clienteServicio.guardarCliente(cliente);
                logger.info(nl+"Cliente agregado "+cliente + nl );
            }
            case 4 ->{
                logger.info(nl+ "----- Modificar Cliente: -----"+nl);
                logger.info("Ingrese el id del cliente que vamos a buscar : ");
                var idCliente = Integer.parseInt(consola.nextLine());
                Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
                if(cliente != null){
                    logger.info("Nombre :");
                    var nombre = consola.nextLine();
                    logger.info("Apellido :");
                    var apellido = consola.nextLine();
                    logger.info("Membresia :");
                    var membresia = Integer.parseInt(consola.nextLine());
                    cliente.setNombre(nombre);
                    cliente.setApellido(apellido);
                    cliente.setMembresia(membresia);
                    clienteServicio.guardarCliente(cliente);
                    logger.info("Cliente actualizado exitosamente"+ cliente);

                }else{
                    logger.info("Cliente no encontrado");
                }
            }
            case 5 ->{
                logger.info(nl+ "----- Eliminar Cliente: -----"+nl);
                logger.info("Ingresa el id del Cliente a eliminar :");
                var id = Integer.parseInt(consola.nextLine());
               var cliente =  clienteServicio.buscarClientePorId(id);
               if(cliente != null){
                   clienteServicio.eliminarCliente(cliente);
                   logger.info(nl+"Cliente eliminado "+cliente);
               }else{
                   logger.info("Error Cliente no encontrado" );
               }

            }
            case 6 ->{salir = true;
            logger.info("vuelva pronto "); }
            default -> logger.info("Opcion invalida ");
        }
        return salir;
    }
}
