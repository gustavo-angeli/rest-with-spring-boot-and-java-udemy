package br.com.gusta.services.v1;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gusta.data.vo.v1.PersonVO;
import br.com.gusta.data.vo.v2.PersonVOV2;
import br.com.gusta.exceptions.ResourceNotFoundException;
import br.com.gusta.mapper.DozerMapper;
import br.com.gusta.mapper.custom.PersonMapper;
import br.com.gusta.model.v1.Person;
import br.com.gusta.repositories.v1.PersonRepositoy;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepositoy repository;
	@Autowired
	PersonMapper mapper;
	
	public List<PersonVO> findAll() {
		logger.info("Finding all persons!");
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
	}
	public List<PersonVOV2> findAllV2() {
		logger.info("Finding all persons!");
		return DozerMapper.parseListObjects(repository.findAll(), PersonVOV2.class);
	}
	
	
	public PersonVO findByID(Long id) {
		
		logger.info("Finding one person!");
		var entity = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("no records found for this id"));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO person) {
		logger.info("Creating one person!");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
		
	}
	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one person with V2!");
		var entity = mapper.convertVOToEntity(person);
		var vo = mapper.convertEntityToVO(repository.save(entity));
		return vo;
		
	}
	public PersonVO update(PersonVO person) {
		logger.info("Updating one person!");
		var entity = repository.findById(person.getId()).orElseThrow( () -> new ResourceNotFoundException("no records found for this id"));
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	public void delete(Long id) {
		logger.info("Deleting one person!");
		repository.deleteById(id);
	}
	
}
