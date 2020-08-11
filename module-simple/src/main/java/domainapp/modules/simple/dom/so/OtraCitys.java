package domainapp.modules.simple.dom.so;

import domainapp.modules.simple.types.Name;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.persistence.jdo.applib.services.IsisJdoSupport_v3_2;

import java.util.List;


@DomainService(
        nature = NatureOfService.VIEW,
        objectType = "simple.OtraCitys"
        )
public class OtraCitys {

    private final RepositoryService repositoryService;
    private final IsisJdoSupport_v3_2 isisJdoSupport;

    public OtraCitys(
            final RepositoryService repositoryService,
            final IsisJdoSupport_v3_2 isisJdoSupport) {
        this.repositoryService = repositoryService;
        this.isisJdoSupport = isisJdoSupport;
    }

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public OtraCity create(
            @Name final String name) {
        return repositoryService.persist(new OtraCity(name));
    }

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    public List<OtraCity> listAll() {
        return repositoryService.allInstances(OtraCity.class);
    }

}
