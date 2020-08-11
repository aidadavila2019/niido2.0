package domainapp.modules.simple.dom.so;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.JDOHelper;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;


import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.datanucleus.identity.DatastoreId;
import org.joda.time.DateTime;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Auditing;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.util.ObjectContracts;

/**
 * Created by AiDa on 12/01/2018.
 */


@javax.jdo.annotations.PersistenceCapable(
        identityType= IdentityType.DATASTORE,
        schema = "simple",
        table = "Role"
)

@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id")

@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "listAllRole", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.so.Role"
        ),
        @javax.jdo.annotations.Query(
                name = "findRoleByRoleId", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.modules.simple.dom.so.Role "
                        + "WHERE roleId == :parRoleId"
        ),
        @javax.jdo.annotations.Query(
        name = "findRoleByDescription", language = "JDOQL",
        value = "SELECT "
                + "FROM domainapp.modules.simple.dom.so.Role "
                + "WHERE description == :parDescription"
)
})

@javax.jdo.annotations.Unique(name="UserRole_Id_UNQ", members = {"roleId"})
@DomainObjectLayout(
        bookmarking = BookmarkPolicy.AS_CHILD,
        cssClassFa = "fa-key"
)

@DomainObject(autoCompleteAction = "findRoleByDescription", autoCompleteRepository = RoleRepository.class,auditing= Auditing.ENABLED )

public class Role implements Comparable<Role> {
    //region > userDeviceId (property)
    private Long roleId;

    @Column(allowsNull="false")
    @MemberOrder(sequence = "1")
    @Title(sequence = "1")
    public Long getRoleId(){
        return roleId;
    }
    public void setRoleId(final Long roleId){
        this.roleId = roleId;
    }
    // endregion

    //region > status (property)
    private String description;
    @Column(allowsNull="false", length = 500)
    @MemberOrder(sequence = "2")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    // endregion

    public Role getRole(){
        return (this);
    }

    //region > creationTime (Property)
    private DateTime creationTime;
    @Column(allowsNull = "true")
    @MemberOrder(sequence = "4")
    public DateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(DateTime creationTime) {
        this.creationTime = creationTime;

    }
    //endregion

    //region > modificationDate (Property)
    private DateTime modificationDate;
    @Column(allowsNull = "true")
    @MemberOrder(sequence = "4")
    public DateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(DateTime modificationDate) {
        this.modificationDate = modificationDate;

    }
    //endregion

    //region > changeDescription (action)
    @MemberOrder(name="Change Description",sequence = "4")
    @ActionLayout(position = ActionLayout.Position.PANEL)
    public void changeDescription(final @ParameterLayout(named="Description") String description) {
        this.setDescription(description);
    }
    //endregion

    //region > changeRoleDate (action)
    @MemberOrder(name="Change RoleDate",sequence = "4")
    @ActionLayout(position = ActionLayout.Position.PANEL)
    public void changeRoleDate(final @ParameterLayout(named="Role Date") DateTime roleDate) {
        this.setModificationDate(roleDate);
    }
    //endregion


    //region > getIdInstance
    public Long getIdInstance() {
        Object objectId = JDOHelper.getObjectId(this);
        if(objectId instanceof DatastoreId) {
            final DatastoreId datastoreId = (DatastoreId) objectId;
            String id = datastoreId.getKeyAsObject().toString();
            long l = Long.parseLong(id);
            return l;
        }
        return null;
    }
    //endregion

    //region > persisted
    public void persisted(){
        if (this.getRoleId() == 0) {
            Long id = this.getIdInstance();
            this.setRoleId(id);
            String sql = "update " + "\"simple\"" + "." + "\"Role\"" + " set "+ "\"roleId\"" + "=" + id + " where id = " + id;
            isisJdoSupport.executeUpdate(sql);
        }
    }
    //endregion

    //region > delete (events)
    public List<Role> delete() {
        // obtain title first, because cannot reference object after deleted
        final String title = container.titleOf(this);
        final List<Role> returnList = null;
        container.removeIfNotAlready(this);
        container.informUser(
                TranslatableString.tr("Deleted {title}", "title", title), this.getClass(), "delete");

        return returnList;
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;
    private ClockService clockService;
    protected ClockService getclockService() {
        return clockService;
    }
    public final void setclockService(final ClockService clockService) {
        this.clockService = clockService;
    }


    //region > compareTo
    @Override
    public int compareTo(final Role other) {
        return ObjectContracts.compare(this, other, "roleId");
    }

    //endregion
    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;
}
