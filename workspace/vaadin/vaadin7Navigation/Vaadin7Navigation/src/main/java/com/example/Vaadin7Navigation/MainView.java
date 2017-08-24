package com.example.Vaadin7Navigation;

import java.util.Iterator;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

public class MainView extends MainViewDesign implements ViewDisplay {
	
	Navigator navigator;
	private final String STYLE_SELECTED = "selected";
	
	public MainView() {
		
		/**
		 * Create navigation for all views
		 * */
		navigator = new Navigator(UI.getCurrent(), (ViewDisplay)this);
		
		addNavigatorView(DashboardView.VIEW_NAME, DashboardView.class, menuButton1);
		addNavigatorView(OrderView.VIEW_NAME, OrderView.class, menuButton2);
		addNavigatorView(AboutView.VIEW_NAME, AboutView.class, menuButton3);
		
		/**
		 * Render to dashboard If none of this view is defined(default view)
		 * */
		if (navigator.getState().isEmpty()) {
			navigator.navigateTo(DashboardView.VIEW_NAME);
		}

		/*navigator.addView(DashboardView.VIEW_NAME, DashboardView.class);
		navigator.addView(OrderView.VIEW_NAME, OrderView.class);
		navigator.addView("about", AboutView.class);
		menuButton1.addClickListener(Event -> doNavigate(DashboardView.VIEW_NAME));
		menuButton2.addClickListener(Event -> doNavigate(OrderView.VIEW_NAME));
		menuButton3.addClickListener(Event -> doNavigate(AboutView.VIEW_NAME));*/
		
		
		/*menuButton1.addClickListener(Event -> scroll_panel.setContent(new DashboardView()));
		menuButton2.addClickListener(Event -> scroll_panel.setContent(new OrderView()));*/
	}
	
	/**
	 * Navigate to particular view
	 * */
	private void doNavigate(String viewName) {
		getUI().getNavigator().navigateTo(viewName);
	}
	
	/**
	 * Bind all views, classes and menu buttons for navigation
	 * */
	private void addNavigatorView(String viewName, Class<? extends View> viewClass, Button menuButton) {
		navigator.addView(viewName, viewClass);
		menuButton.addClickListener(Event -> doNavigate(viewName));
		menuButton.setData(viewClass.getName());
	}
	
	/**
	 * Add/Remove style based on selected menu buttons
	 * */
	private void adjustStyleByData(Component component, Object data) {
		System.out.println("data::" + data);
		System.out.println("button::" + ((Button)component).getData());
		
        if (component instanceof Button) {
            if (data != null && data.equals(((Button) component).getData())) {
                component.addStyleName(STYLE_SELECTED);
            } else {
                component.removeStyleName(STYLE_SELECTED);
            }
        }
    }

	/**
	 * Bind selected view with scroll panel
	 * */
	@Override
	public void showView(View view) {
		if (view instanceof Component) {
			scroll_panel.setContent((Component) view);

			Iterator iterator = side_bar.iterator();
			while (iterator.hasNext()) {
				adjustStyleByData((Component)iterator.next(), view.getClass().getName());
			}
		} else {
			throw new IllegalArgumentException("View is not component");
		}
	}

}
