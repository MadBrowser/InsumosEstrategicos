package cl.colabra.cvilches.insumosestrategicos.model;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

import cl.colabra.cvilches.insumosestrategicos.utils.SGIEDataBase;

/**
 * Project: InsumosEstrategicos.
 * Created by Carlos Vilches on 12/14/15. By appointment
 * of Colabra for client 'Minera Collahuasi'
 */
@Table(databaseName = SGIEDataBase.NAME)
public class Storehouse extends BaseModel {

    // Constants
    public static final String GREEN_LIGHTS = "Verde";
    public static final String YELLOW_LIGHTS = "Amarillo";
    public static final String RED_LIGHTS = "Rojo";

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
    private Date lastReading;

    public Storehouse(long id, String description, float percentageStock, String stockLight, Date lastReading) {
        this.id = id;
        this.description = description;
        this.percentageStock = percentageStock;
        this.stockLight = stockLight;
        this.lastReading = lastReading;
    }

    public long getId() {
        return id;
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

    public Date getLastReading() {
        return lastReading;
    }

    public void setLastReading(Date lastReading) {
        this.lastReading = lastReading;
    }
}
