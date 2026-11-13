# Inventory Management App

A JavaFX desktop application for managing products in an SQLite inventory database.

## Features

- Add, update, search, and delete inventory records
- Track product details, including quantity, price, and supplier
- Calculate total inventory value
- Export search results to CSV

## Built with

Java, JavaFX, SQLite, and Maven.
# Inventory Management App

A JavaFX desktop application for managing products in an SQLite inventory database.

## Features

- Add, update, search, and delete inventory records
- Track product details, including quantity, price, and supplier
- Calculate total inventory value
- Export search results to CSV

## Built with

Java, JavaFX, SQLite, and Maven.

## Run

You’ll need a JDK and Maven. From the repository root, run:

```bash
mvn clean javafx:run
```

The Maven configuration currently selects the JavaFX `mac-aarch64` platform. Update the `javafx.platform` value in `pom.xml` if you’re using a different platform.

Before launching the app, update the SQLite database location in `DB.java` to a path on your computer. The current source uses a developer-specific absolute path.## Run

You’ll need a JDK and Maven. From the repository root, run:

```bash
mvn clean javafx:run
```

The Maven configuration currently selects the JavaFX `mac-aarch64` platform. Update the `javafx.platform` value in `pom.xml` if you’re using a different platform.

Before launching the app, update the SQLite database location in `DB.java` to a path on your computer. The current source uses a developer-specific absolute path.
