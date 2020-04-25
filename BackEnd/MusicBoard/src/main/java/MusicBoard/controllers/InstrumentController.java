package MusicBoard.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import MusicBoard.entities.Instrument;
import MusicBoard.entities.wrapper.Instruments;
import MusicBoard.repositories.InstrumentRepository;

@RestController
@RequestMapping("/instrument")
public class InstrumentController {
    
    @Autowired
    private InstrumentRepository instrumentRepository;
    
    @GetMapping("")
    public ResponseEntity<Instruments> getAll(){
        final List<Instrument> result = Lists.newArrayList();
        instrumentRepository.findAll().iterator().forEachRemaining(result::add);
        return new ResponseEntity(Instruments.of(result), HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<Instrument> post(@RequestBody Instrument instrument) {
        return ResponseEntity.ok(instrumentRepository.save(instrument));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Instrument> getById(@PathVariable Integer id){
       Optional<Instrument> oInstrument = instrumentRepository.findById(id);
        if (!oInstrument.isPresent()) {
            return ResponseEntity.notFound().build();
        } 
        return ResponseEntity.ok(oInstrument.get());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Instrument> updateById(@PathVariable Integer id, @RequestBody Instrument instrument) {
        Optional<Instrument> oInstrument = instrumentRepository.findById(id);
        if (oInstrument.isPresent()) {
            instrument.setId(id);
            return ResponseEntity.ok(instrumentRepository.save(instrument));
        }
        return ResponseEntity.notFound().build();
    }
    
            
    @DeleteMapping("/{id}")
    public ResponseEntity<Instrument> deleteById(@PathVariable Integer id) {
        Optional<Instrument> oInstrument = instrumentRepository.findById(id);
        if (!oInstrument.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        instrumentRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<Instrument> getByName(@PathVariable String name){
       Optional<Instrument> oInstrument = instrumentRepository.findByName(name);
        if (!oInstrument.isPresent()) {
            return ResponseEntity.notFound().build();
        } 
        return ResponseEntity.ok(oInstrument.get());
    }
    
    @PutMapping("/name/{name}")
    public ResponseEntity<Instrument> updateByName(@PathVariable String name, @RequestBody Instrument instrument) {
        Optional<Instrument> oInstrument = instrumentRepository.findByName(name);
        if (oInstrument.isPresent()) {
            instrument.setId(oInstrument.get().getId());
            return ResponseEntity.ok(instrumentRepository.save(instrument));
        }
        return ResponseEntity.notFound().build();
    }
    
            
    @DeleteMapping("/name/{name}")
    public ResponseEntity<Instrument> deleteByName(@PathVariable String name) {
        Optional<Instrument> oInstrument = instrumentRepository.findByName(name);
        if (!oInstrument.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        instrumentRepository.delete(oInstrument.get());
        return ResponseEntity.ok().build();
    }
    
}
