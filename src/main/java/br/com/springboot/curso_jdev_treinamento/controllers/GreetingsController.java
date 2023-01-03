package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /* INJEÇÃO DE DEPENDENCIA*/
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
//    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
//    @ResponseStatus(HttpStatus.OK)
//    public String greetingText(@PathVariable String name) {
//        return "Hello " + name + "!";
//    }
    
    @RequestMapping(value = "/texto/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String metodoRetornoTexto(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	usuarioRepository.save(usuario);
    	
    	return "Retorno Texto " + nome;
    }
    
    @GetMapping(value = "listatodos")/* API DE LISTA */
    @ResponseBody /* RETORNA OS DADOS PARA O CORPO DA RESPOSTA */
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    	List<Usuario> usuarios = usuarioRepository.findAll(); /* EXECUTA A CONSULTA NO BD*/
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /* RETORNA A LISTA EM JSON*/ 	
    }
    
    @PostMapping(value = "salvar")
    @ResponseBody
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ /* RECEBE OS DADOS PARA SALVAR*/
    	
    	Usuario usuarioSalvo = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);
    }
    
    @PutMapping(value = "atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ /* RECEBE OS DADOS PARA ALTERAR*/
    	
    	if (usuario.getId() == null) {
    		return new ResponseEntity<String>("ID não informado", HttpStatus.OK);
    	}
    	
    	Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.OK);
    }
    
    @DeleteMapping(value = "deletar")
    @ResponseBody
    public ResponseEntity<String> deletar(@RequestParam Long iduser){
    	
    	usuarioRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>("User Deletado", HttpStatus.OK);
    }
    
    @GetMapping(value = "pesquisaId")
    @ResponseBody
    public ResponseEntity<Usuario> pesquisaId(@RequestParam(name = "iduser") Long iduser){
    	
    	Usuario usuario = usuarioRepository.findById(iduser).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }
    
    @GetMapping(value = "pesquisaPorNome")
    @ResponseBody
    public ResponseEntity<List<Usuario>> pesquisaPorNome(@RequestParam(name = "name") String name){
    	
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase()); /* COM O .TRIM IGNORA ESPAÇO */
    	
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    }
    
    
    
}
