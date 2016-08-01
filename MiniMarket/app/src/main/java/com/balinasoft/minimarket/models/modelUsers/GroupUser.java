package com.balinasoft.minimarket.models.modelUsers;

import com.balinasoft.minimarket.interfaces.Title;

public class GroupUser implements Title
{
    private int id;

    private String group;

    public GroupUser(int id, String group) {
        this.id = id;
        this.group = group;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getGroup ()
    {
        return group;
    }

    public void setGroup (String group)
    {
        this.group = group;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", group = "+group+"]";
    }

    @Override
    public String getTitle() {
        return group;
    }
}