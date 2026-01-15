package ryot.zona_fit.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import ryot.zona_fit.modelo.Cliente;

public interface ClienteRepo  extends JpaRepository<Cliente,Integer> {

}
