package domainapp.modules.simple.dom.so;

import domainapp.modules.simple.types.Name;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.message.MessageService;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.services.title.TitleService;

import javax.inject.Inject;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import java.util.Comparator;


@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE, schema = "simple" )
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy= VersionStrategy.DATE_TIME, column ="version")
@javax.jdo.annotations.Unique(name="city_name_UNQ", members = {"city"})
@DomainObject(auditing = Auditing.ENABLED)
@DomainObjectLayout()  // causes UI events to be triggered
public class OtraCity implements Comparable<OtraCity> {

    private OtraCity(){}

    public OtraCity(final String city) {
        this.city = city;
    }

    public String title() {
        return "Object: " + getCity();
    }

    @Name
    @MemberOrder(name = "identity", sequence = "1")
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String name) {
        this.city = city;
    }


    @Action(semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE, associateWith = "city")
    @ActionLayout(position = ActionLayout.Position.PANEL)
    public void delete() {
        final String title = titleService.titleOf(this);
        messageService.informUser(String.format("'%s' deleted", title));
        repositoryService.removeAndFlush(this);
    }

    @Override
    public String toString() {
        return getCity();
    }

    @Override
    public int compareTo(final OtraCity other) {
        return Comparator.comparing(OtraCity::getCity).compare(this, other);
    }

    @Inject RepositoryService repositoryService;
    @Inject TitleService titleService;
    @Inject MessageService messageService;

}