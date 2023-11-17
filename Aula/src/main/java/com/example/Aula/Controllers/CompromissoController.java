package com.example.Aula.Controllers;

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

import com.example.Aula.Entities.Compromisso;
import com.example.Aula.Repositories.CompromissoRepository;

@RestController
@RequestMapping("/compromissos")
public class CompromissoController {
	@Autowired
	CompromissoRepository repo;

	@GetMapping()
	public ResponseEntity<List<Compromisso>> getCompromissos() {
		return ResponseEntity.status(HttpStatus.OK).body(repo.findAll());
	}

	@GetMapping("/{localName}")
	public ResponseEntity<List<Compromisso>> getCompromissosPorLocal(String localName) {
		List<Compromisso> comps = repo.findAll();
		for (Compromisso comp : comps) {
            if (!comp.getLocal().getNome().equals(localName)) {
                comps.remove(comp);
            }
	    }
		return ResponseEntity.status(HttpStatus.OK).body(comps);
	}

	@PostMapping()
	public ResponseEntity<Compromisso> inserirCompromisso(@RequestBody Compromisso compromisso) {
		Compromisso cm = repo.save(compromisso);
		return ResponseEntity.status(HttpStatus.CREATED).body(cm);
	}

	@PutMapping("/{idCompromisso}")
	public ResponseEntity<Compromisso> alterarCompromisso(@PathVariable("idCompromisso") Long idCompromisso,
			String status) {
		Optional<Compromisso> opCompromisso = repo.findById(idCompromisso);
		try {
			Compromisso cm = opCompromisso.get();
			cm.setStatus(status);
			repo.save(cm);
			return ResponseEntity.status(HttpStatus.OK).body(cm);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Compromisso> getUmCompromisso(@PathVariable("id") long id) {
		Optional<Compromisso> opCompromisso = repo.findById(id);
		try {
			Compromisso compromisso = opCompromisso.get();
			return ResponseEntity.status(HttpStatus.OK).body(compromisso);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Compromisso> excluirUmCompromisso(@PathVariable("id") long id) {
		Optional<Compromisso> opCompromisso = repo.findById(id);
		try {
			Compromisso cm = opCompromisso.get();
			repo.delete(cm);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}