package tk.djandjiev.practice.service.organization;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.djandjiev.practice.dao.organization.OrganizationRepository;
import tk.djandjiev.practice.model.Organization;
import tk.djandjiev.practice.service.mapper.MapperFacade;
import tk.djandjiev.practice.to.OrganizationTO;
import tk.djandjiev.practice.to.SimplifiedOrganizationTO;

/**
 * Реализация интерфейса OrganizationService.
 * @see tk.djandjiev.practice.service.organization.OrganizationService
 * */
@Service
public class OrganizationServiceImpl implements OrganizationService {

  private final MapperFacade facade;
  private final OrganizationRepository repository;

  @Autowired
  public OrganizationServiceImpl(MapperFacade facade,
      OrganizationRepository repository) {
    this.facade = facade;
    this.repository = repository;
  }

  @Override
  public List<SimplifiedOrganizationTO> getAll(@NotNull String name, String inn, Boolean isActive) {
    return facade.mapAsList(
        repository.getAll(name, inn, isActive),
        SimplifiedOrganizationTO.class);
  }

  @Override
  public OrganizationTO get(Integer id) {
    return facade.map(repository.get(id), OrganizationTO.class);
  }

  @Override
  @Transactional
  public void create(OrganizationTO org) {
    Organization result = new Organization();
    facade.map(org, result);
    repository.save(result);
  }

  @Override
  @Transactional
  public void update(OrganizationTO org) {
    Organization result = repository.get(org.getId());
    facade.map(org, result);
    repository.save(result);
  }
}