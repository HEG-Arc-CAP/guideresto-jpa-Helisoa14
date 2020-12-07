package ch.hearc.ig.guideresto.service;

import ch.hearc.ig.guideresto.business.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class RestaurantService {
    //Création
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("guideRestoPersistenceUnit");

    public City insertCity(String cityZipCode, String cityName) {

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        City city = new City(cityZipCode, cityName);
        entityManager.persist(city);

        transaction.commit();

        return city;
    }

    public BasicEvaluation insertBasicEvaluation(BasicEvaluation basicEvaluation) {
        return null;
    }

    public Set<Restaurant> researchAllRestaurants() {
        Set<Restaurant> restaurants = new HashSet<>();

        EntityManager entityManager = emf.createEntityManager();
       // EntityTransaction transaction = entityManager.getTransaction();
       // Pas besoin de transaction car on est sur un SELECT

        //find (class, type de la PK)
        //boucle
        //Le JPQL est toujours fait sur les classes
        restaurants = new HashSet<>(entityManager.createQuery("SELECT r FROM Restaurant r").getResultList());

        return restaurants;
    }

    public Restaurant researchById(Integer restaurantId) {
        Set<Restaurant> restaurants = new HashSet<>();

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Restaurant restaurant = entityManager.find(Restaurant.class, 1);
        restaurants.add(restaurant);

        transaction.commit();
        return restaurant;
    }

    public Set<Restaurant> researchByName(String name) {
        return Collections.emptySet();
    }

    public Set<Restaurant> researchByCity(String cityName) {
        return Collections.emptySet();
    }

    public Set<City> researchAllCities() {
        Set<City> cities = new HashSet<>();

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        //find (class, type de la PK)
        City city = entityManager.find(City.class, 1);
        cities.add(city);

        transaction.commit();

        return Collections.emptySet();
    }

    public Set<RestaurantType> researchAllRestaurantTypes() {
        return Collections.emptySet();
    }

    public Restaurant insertRestaurant(Restaurant restaurant) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        //Remonter dans la méthode pour voir dans quel état il est
        entityManager.persist(restaurant);

        transaction.commit();

        return restaurant;
    }

    public Set<EvaluationCriteria> researchAllEvaluationCritierias() {
        return Collections.emptySet();
    }

    public CompleteEvaluation insertCompleteEvaluation(CompleteEvaluation completeEvaluation) {
        return null;
    }

    public Restaurant updateRestaurant(Restaurant restaurant) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.detach(restaurant);

        transaction.commit();
        return null;
    }

    public void deleteRestaurant(Restaurant restaurant) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.detach(restaurant);

        transaction.commit();
    }

    public Set<Restaurant> researchByType(RestaurantType type) {
        return Collections.emptySet();
    }
}
