package domainapp.modules.simple.dom.so;

import java.util.List;

import javax.inject.Inject;
import org.joda.time.DateTime;

import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;


/**
 * Created by AiDa on 12/01/2018.
 */

@DomainService(repositoryFor = Role.class)
@DomainServiceLayout(named = "Role")

public class RoleRepository {

    //region > listAllRole (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(name="Role",sequence = "1")
    public List<Role> listAllRole() {
        return container.allInstances(Role.class);
    }
    //endregion


    //region > findRoleByDescription (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.NEVER
    )
    @MemberOrder(sequence = "2")
    public List<Role> findRoleByDescription(
            @ParameterLayout(named = "RoleId")
            final String parDescription
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        Role.class,
                        "findRoleByDescription",
                        "parDescription", parDescription));
    }
    //endregion

    //region > findRoleByRoleId (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.NEVER
    )
    @MemberOrder(sequence = "2")
    public List<Role> findRoleByRoleId(
            @ParameterLayout(named = "RoleId")
            final int parRoleId
    ) {
        return container.allMatches(
                new QueryDefault<>(
                        Role.class,
                        "findRoleByRoleId",
                        "parRoleId", parRoleId));
    }
    //endregion


    //region > addRole(action)
    public static class CreateDomainEvent extends ActionDomainEvent<RoleRepository> {
        public CreateDomainEvent(final RoleRepository source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }
    }

    @Action(
            //domainEvent = CreateDomainEvent.class
    )
    @MemberOrder(name="Role",sequence = "2")
    public Role addRole(
            final @ParameterLayout(named="Description") String description

    )
    {
        final Role obj = container.newTransientInstance(Role.class);
        Long roleId = new Long(0);
        obj.setRoleId(roleId);
        obj.setDescription(description);
        obj.setCreationTime(clockService.nowAsJodaDateTime());
        obj.setModificationDate(clockService.nowAsJodaDateTime());

        container.persistIfNotAlready(obj);
        return (obj) ;
    }
    //endregion


    //region > injected services

    //@javax.inject.Inject
    DomainObjectContainer container;

    //endregion
    @Inject
    private ClockService clockService;
    protected ClockService getclockService() {
        return clockService;
    }
    public final void setclockService(final ClockService clockService) {
        this.clockService = clockService;
    }

}
