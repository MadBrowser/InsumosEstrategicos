package cl.colabra.cvilches.insumosestrategicos.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.builder.ConditionQueryBuilder;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.utils.SGIEDataBase;

/**
 * Project: InsumosEstrategicos.
 * Created by Carlos Vilches on 12/17/15. By appointment
 * of Colabra for client 'Minera Collahuasi'
 */
@ModelContainer
@Table(databaseName = SGIEDataBase.NAME)
public class DailyPlan extends BaseModel {

    public static final String ON_EXECUTION = "cl.colabra.on_execution";
    public static final String DONE = "cl.colabra.done";
    public static final String EXPIRED = "cl.colabra.expired";
    public List<Register> registerList;

    @Column
    @PrimaryKey(autoincrement = true)
    private long id;

    @Column
    private Date createdAt;

    @Column
    private String status;

    // Empty constructor required for DB Flow
    public DailyPlan() {
        this.createdAt = new Date();
        this.status = ON_EXECUTION;
        this.registerList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "registerList")
    public List<Register> getMyRegisters() {
        if (registerList == null || registerList.isEmpty()) {
            registerList = new Select()
                    .from(Register.class)
                    .where(Condition.column(Register$Table.DAILYPLANFKCONTAINER_DAILY_PLAN_ID)
                        .is(id))
                    .queryList();
        }
        return registerList;
    }

    public List<Register> getMyPendingRegisters() {
        return new Select()
                .from(Register.class)
                .where(Condition.column(Register$Table.DAILYPLANFKCONTAINER_DAILY_PLAN_ID).is(id))
                .and(Condition.column("status").is(Register.PENDING))
                .queryList();
    }
}
