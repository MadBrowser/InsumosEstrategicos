package cl.colabra.cvilches.insumosestrategicos.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.builder.Condition;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

import cl.colabra.cvilches.insumosestrategicos.utils.SGIEDataBase;

/**
 * Project: InsumosEstrategicos.
 * Created by Carlos Vilches on 12/14/15. By appointment
 * of Colabra for client 'Minera Collahuasi'
 */
@ModelContainer
@Table(databaseName = SGIEDataBase.NAME)
public class Storehouse extends BaseModel {

    public static final String GREEN_LIGHTS = "Verde";
    public static final String YELLOW_LIGHTS = "Amarillo";
    public static final String RED_LIGHTS = "Rojo";
    public List<Register> registerList;

    @Column
    @PrimaryKey
    private long id;
    @Column
    private String description;
    @Column
    private float percentageStock;
    @Column
    private String stockLight;
    @Column
    private String lastReading;
    @Column
    private float capacity;
    @Column
    private float softCap;
    @Column
    private float softCapCapacity;
    @Column
    private float hardCap;
    @Column
    private float hardCapCapacity;
    @Column
    private float stock;

    // Empty constructor required for DB Flow
    public Storehouse() {
        this.registerList = new ArrayList<>();
    }

    public Storehouse(long id, String description, float percentageStock, String stockLight,
                      String lastReading, float capacity, float softCap, float softCapCapacity,
                      float hardCap, float hardCapCapacity, float stock) {
        this.id = id;
        this.description = description;
        this.percentageStock = percentageStock;
        this.stockLight = stockLight;
        this.lastReading = lastReading;
        this.capacity = capacity;
        this.softCap = softCap;
        this.softCapCapacity = softCapCapacity;
        this.hardCap = hardCap;
        this.hardCapCapacity = hardCapCapacity;
        this.stock = stock;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPercentageStock() {
        return percentageStock;
    }

    public void setPercentageStock(float percentageStock) {
        this.percentageStock = percentageStock;
    }

    public String getStockLight() {
        return stockLight;
    }

    public void setStockLight(String stockLight) {
        this.stockLight = stockLight;
    }

    public String getLastReading() {
        return lastReading;
    }

    public void setLastReading(String lastReading) {
        this.lastReading = lastReading;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public float getSoftCap() {
        return softCap;
    }

    public void setSoftCap(float softCap) {
        this.softCap = softCap;
    }

    public float getSoftCapCapacity() {
        return softCapCapacity;
    }

    public void setSoftCapCapacity(float softCapCapacity) {
        this.softCapCapacity = softCapCapacity;
    }

    public float getHardCap() {
        return hardCap;
    }

    public void setHardCap(float hardCap) {
        this.hardCap = hardCap;
    }

    public float getHardCapCapacity() {
        return hardCapCapacity;
    }

    public void setHardCapCapacity(float hardCapCapacity) {
        this.hardCapCapacity = hardCapCapacity;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    @OneToMany(methods = {OneToMany.Method.SAVE, OneToMany.Method.DELETE}, variableName = "registerList")
    public List<Register> getMyRegisters() {
        if (registerList == null || registerList.isEmpty()) {
            registerList = new Select()
                    .from(Register.class)
                    .where(Condition.column(Register$Table.STOREHOUSEFKCONTAINER_STOREHOUSE_ID)
                            .is(id))
                    .queryList();
        }
        return registerList;
    }
}