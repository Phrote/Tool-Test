package MusicBoard.repositories;

import java.util.Optional;
import MusicBoard.entities.Instrument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends CrudRepository<Instrument, Integer> {
    
    Optional<Instrument> findByName(String name);
    Optional<Instrument> findByInstrumentType(String InstrumentType);
    
}
