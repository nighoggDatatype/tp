package seedu.address.logic.filters;

import seedu.address.model.customer.Car;

/**
 * Filters Cars based on user specified filters carBrand, carType.
 */
public abstract class CarFilter extends Filter {
    /**
     * Creates a filter for Cars
     *
     * @param filterString the filter to match the {@code Customer}
     */
    public CarFilter(String filterString) {
        super(filterString);
    }

    /**
     * Predicate for abstract test method use.
     *
     * @param car       Car member from carsOwned or carsPreferred.
     * @param filterCar Specified Car to filter by.
     * @return whether the car satisfies the filter string
     */
    public boolean carFilterPredicate(Car car, String filterCar) {
        filterCar = filterCar.trim();
        String[] carDetails = filterCar.split("\\+", 2);
        if (carDetails.length == 1) {

            return car.getCarBrand().toLowerCase().contains(filterCar.toLowerCase())
                || car.getCarType().toLowerCase().contains(filterCar.toLowerCase());
        } else {
            return car.getCarBrand().toLowerCase().contains(carDetails[0].toLowerCase())
                || car.getCarType().toLowerCase().contains(carDetails[1].toLowerCase());
        }
    }
}
