package domainapp.modules.simple.dom.so;

import domainapp.modules.simple.SimpleModule;
import domainapp.modules.simple.types.Name;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.IsisJdoSupport_v3_2;

import java.util.List;


@DomainService(
        nature = NatureOfService.VIEW,
        objectType = "simple.CityRepository"
)
public class CityRepository {

    private final RepositoryService repositoryService;
    private final IsisJdoSupport_v3_2 isisJdoSupport;

    public CityRepository(
            final RepositoryService repositoryService,
            final IsisJdoSupport_v3_2 isisJdoSupport) {
        this.repositoryService = repositoryService;
        this.isisJdoSupport = isisJdoSupport;
    }

    public static class ActionDomainEvent extends SimpleModule.ActionDomainEvent<CityRepository> {}

    //@Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    //@ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public static class CreateActionDomainEvent extends CityRepository.ActionDomainEvent {}
    @Action(semantics = SemanticsOf.NON_IDEMPOTENT, domainEvent = CityRepository.CreateActionDomainEvent.class)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_SIDEBAR)
    public City create(
            @Name final String name) {
        return repositoryService.persist(new City(name));
    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    public List<City> listAll() {
        return repositoryService.allInstances(City.class);
    }

}
