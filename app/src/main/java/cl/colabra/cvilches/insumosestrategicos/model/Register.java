package cl.colabra.cvilches.insumosestrategicos.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.container.ForeignKeyContainer;

import cl.colabra.cvilches.insumosestrategicos.utils.SGIEDataBase;

/**
 * Project: InsumosEstrategicos.
 * Created by Carlos Vilches on 12/17/15. By appointment
 * of Colabra for client 'Minera Collahuasi'
 */
@Table(databaseName = SGIEDataBase.NAME)
public class Register extends BaseModel {

    public static final String PENDING = "cl.colabra.pending";
    public static final String REGISTERED = "cl.colabra.registered";
    public static final String SYNCED = "cl.colabra.synced";
    public static final String EXPIRED = "cl.colabra.expired";

    @Column
    @PrimaryKey(autoincrement = true)
    private long id;

    @Column
    private String status;

    private String comment;

    // Empty constructor required for DB Flow
    public Register() {
    }

    public Register(ForeignKeyContainer<DailyPlan> dailyPlanFKContainer,
                    ForeignKeyContainer<Storehouse> storehouseFKContainer) {
        this.dailyPlanFKContainer = dailyPlanFKContainer;
        this.storehouseFKContainer = storehouseFKContainer;
        this.status = PENDING;
    }

    @Column
    @ForeignKey(references = {
            @ForeignKeyReference(columnName = "daily_plan_id", columnType = Long.class,
                    foreignColumnName = "id")},
            saveForeignKeyModel = false)
    public ForeignKeyContainer<DailyPlan> dailyPlanFKContainer;

    @Column
    @ForeignKey(references = {
            @ForeignKeyReference(columnName = "storehouse_id", columnType = Long.class,
                    foreignColumnName = "id")},
            saveForeignKeyModel = false)
    public ForeignKeyContainer<Storehouse> storehouseFKContainer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
