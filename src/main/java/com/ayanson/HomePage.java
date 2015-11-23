package com.ayanson;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

public class HomePage extends WebPage {

    Form<Void> form =null;

    public HomePage()
    {
        final Sql sql = new Sql();
        IModel dataList =  new LoadableDetachableModel()
        {
            protected Object load() {
                return sql.getItems();
            }
        };

        ListView dataView = new ListView("dataList", dataList)
        {
            protected void populateItem(final ListItem item) {
                RecordItem recordItem = (RecordItem)item.getModelObject();
                item.add(new Label("itemId", recordItem.getItemId()));
                item.add(new Label("groupId", recordItem.getGroupId()));
            }
        };

        final WebMarkupContainer listContainer = new WebMarkupContainer("theContainer");
        listContainer.setOutputMarkupId(true);
        listContainer.add(dataView);
        add(listContainer);

        //refreshing
        AjaxLink<Void> refreshButton = new AjaxLink<Void>("refreshButton") {
            public void onClick(AjaxRequestTarget target) {

                if (target != null) {
                    target.add(listContainer);
                }
            }
        };
        add(refreshButton);

        //add new records
        form= new Form<Void>("form");
        final TextField <String> inputItemId = new TextField("inputItemId",new Model<String>(""));
        final TextField <String> inputGroupId = new TextField("inputGroupId",new Model<String>(""));
        form.add(inputItemId);
        form.add(inputGroupId);

        AjaxButton ajaxButton=new AjaxButton("addButton") {
            protected void onSubmit(AjaxRequestTarget target, Form form) {

                if (target!=null)
                {
                    String itemId=form.getRequest().getRequestParameters().getParameterValue("inputItemId").toString();
                    String groupId=form.getRequest().getRequestParameters().getParameterValue("inputGroupId").toString();
                    try {
                        sql.addItem(Integer.parseInt(itemId), Integer.parseInt(groupId));
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    target.add(listContainer);
                }
            }
        };

        form.add(ajaxButton);
        add(form);
    }

}