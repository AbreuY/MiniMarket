package com.balinasoft.mallione.models.modelUsers;

import com.balinasoft.mallione.interfaces.Title;

public class Supplier implements Title
{
    private int supplier_id;

    private String o_inn;

    public int getSupplier_id ()
    {
        return supplier_id;
    }

    public void setSupplier_id (int supplier_id)
    {
        this.supplier_id = supplier_id;
    }

    public String getO_inn ()
    {
        return o_inn;
    }

    public void setO_inn (String o_inn)
    {
        this.o_inn = o_inn;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [supplier_id = "+supplier_id+", o_inn = "+o_inn+"]";
    }

    @Override
    public String getTitle() {
        return o_inn;
    }
}
