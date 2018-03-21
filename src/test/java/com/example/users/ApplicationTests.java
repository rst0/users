package com.example.users;

import com.example.users.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationTests {

	@Autowired
	private CrudRepository<User, Long> repository;

	@Before
	public void startup() {
		repository.deleteAll();
	}

	@Test
	public void pingTest() {
		given()
		.when()
				.get("/users")
		.then()
				.statusCode(200);
	}

	@Test
	public void createUserTest() {
		User user = new User(0L, "John", "Doe");

		given()
				.contentType("application/json")
				.body(user)
		.when()
				.post("/users")
		.then()
				.statusCode(200)
				.body("id", not(equalTo(user.getId())))
				.body("firstName", equalTo(user.getFirstName()))
				.body("lastName", equalTo(user.getLastName()));
	}

	@Test
	public void getAllUsersTest() {
		List<User> expected =
				Stream.of(
						new User(0L, "John", "Doe"),
						new User(0L, "Bill", "Evans"),
						new User(0L, "Justin", "McGregor"))
				.map(user -> repository.save(user))
				.collect(Collectors.toList());

		List<User> actual =
				given()
				.when()
					.get("/users")
				.then()
					.statusCode(200)
					.extract().response().body().jsonPath().getList("", User.class);

		Assert.assertThat(actual, hasSize(expected.size()));
		Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
	}

	@Test
	public void getUserByIdTest() {
		User user = repository.save(new User(0L, "John", "Doe"));

		given()
		.when()
				.get("/users/" + user.getId())
		.then()
				.statusCode(200)
				.body("id", equalTo((int)user.getId()))
				.body("firstName", equalTo(user.getFirstName()))
				.body("lastName", equalTo(user.getLastName()));
	}

	@Test
	public void updateUserTest() {
		User expected = repository.save(new User(0L, "John", "Doe"));

		expected.setFirstName("Bill");

		given()
				.contentType("application/json")
				.body(expected)
		.when()
				.put("/users")
		.then()
				.statusCode(200)
				.body("id", equalTo((int)expected.getId()))
				.body("firstName", equalTo(expected.getFirstName()))
				.body("lastName", equalTo(expected.getLastName()));

		User actual = repository.findOne(expected.getId());

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void deleteUserTest() {
		List<User> expected =
				Stream.of(
						new User(0L, "John", "Doe"),
						new User(0L, "Bill", "Evans"),
						new User(0L, "Justin", "McGregor"))
						.map(user -> repository.save(user))
						.collect(Collectors.toList());

		User victim = expected.get(1);

		given()
				.contentType("application/json")
				.body(victim)
		.when()
				.delete("/users")
		.then()
				.statusCode(200);

		expected.remove(victim);

		List<User> actual = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());

		Assert.assertThat(actual, hasSize(expected.size()));
		Assert.assertTrue(expected.containsAll(actual) && actual.containsAll(expected));
	}
}
