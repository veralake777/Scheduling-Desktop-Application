package PresentationState.infra;

import DAO.POJO.City;
import DAO.POJO.Country;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Comparator;

public class JavaFxWidgetBindings {

	public static void bindTextField(TextField textField, StringProperty stringProperty) {
		textField.textProperty().bindBidirectional(stringProperty);
	}
	public static void bindLabel(Label label, StringProperty stringProperty) {
		label.textProperty().bind(stringProperty);

	}
	public static void bindButton(Button button, InvalidationListener invalidationListener) {
		button.setOnAction(event -> triggerAction(invalidationListener));
	}
	public static void bindDatePicker(DatePicker datePicker, StringProperty stringProperty) {
		datePicker.promptTextProperty().bind(stringProperty);
	}


	public static void populateCityComboBox(ComboBox<City> cityComboBox, ObservableList<City> observableList) throws ParseException, SQLException, ClassNotFoundException {
		observableList.sort(Comparator.comparing(City::getCity));
		cityComboBox.setItems(observableList);
		cityComboBox.setPromptText("City");

		// Define rendering of the list of values in ComboBox drop down.
		cityComboBox.setCellFactory(lv -> new ListCell<City>() {
			@Override
			protected void updateItem(City city, boolean empty) {
				super.updateItem(city, empty);
				setText(empty ? null : city.getCity());
			}
		});

		// Define rendering of selected value shown in ComboBox.
		cityComboBox.setConverter(new StringConverter<City>() {
			@Override
			public String toString(City city) {
				if (city == null) {
					return null;
				} else {
					return city.getCity();
				}
			}

			@Override
			public City fromString(String cityString) {
				return null; // No conversion fromString needed.
			}
		});
	}

	public static void populateCountryComboBox(ComboBox<Country> countryComboBox, ObservableList<Country> observableList) throws ParseException, SQLException, ClassNotFoundException {
		observableList.sort(Comparator.comparing(Country::getCountry));
		countryComboBox.setItems(observableList);
		countryComboBox.setPromptText("Country");

		// Define rendering of the list of values in ComboBox drop down.
		countryComboBox.setCellFactory(lv -> new ListCell<Country>() {
			@Override
			protected void updateItem(Country country, boolean empty) {
				super.updateItem(country, empty);
				setText(empty ? null : country.getCountry());
			}
		});

		// Define rendering of selected value shown in ComboBox.
		countryComboBox.setConverter(new StringConverter<Country>() {
			@Override
			public String toString(Country country) {
				if (country == null) {
					return null;
				} else {
					return country.getCountry();
				}
			}

			@Override
			public Country fromString(String countryString) {
				return null; // No conversion fromString needed.
			}
		});
	}

	public static void bindComboBox(ComboBox comboBox, InvalidationListener invalidationListener) {
		comboBox.setOnAction(event -> triggerAction(invalidationListener));
	}

	public static void bindListToComboBox(ComboBox<Integer> idComboBox, ObservableList<Integer> ids) {
		idComboBox.setItems(ids);
	}

	public static void triggerAction(InvalidationListener invalidationListener) {
		invalidationListener.invalidated(null);
	}
}
