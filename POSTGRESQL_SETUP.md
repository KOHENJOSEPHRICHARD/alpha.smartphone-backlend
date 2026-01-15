# PostgreSQL Setup Guide for Alpha.SmartPhone

This guide will help you set up PostgreSQL database for the Alpha.SmartPhone backend.

## Step 1: Install PostgreSQL

### Windows
1. Download PostgreSQL from: https://www.postgresql.org/download/windows/
2. Run the installer (recommended version: 15 or higher)
3. During installation, note down:
   - Port: `5432` (default)
   - Username: `postgres` (default)
   - Password: **SET A STRONG PASSWORD**

### macOS
```bash
brew install postgresql@15
brew services start postgresql@15
```

### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
sudo systemctl start postgresql
sudo systemctl enable postgresql
```

## Step 2: Verify PostgreSQL is Running

### Windows
- Open **Services** (Win + R → `services.msc`)
- Find **postgresql-x64-15** service
- Status should be **Running**

Or use Command Prompt:
```cmd
psql -U postgres -c "SELECT version();"
```

### macOS/Linux
```bash
psql --version
sudo systemctl status postgresql
```

## Step 3: Create Database

Open PostgreSQL command line:

### Windows
```cmd
psql -U postgres
```

### macOS/Linux
```bash
sudo -u postgres psql
```

Then run these SQL commands:

```sql
-- Create the database
CREATE DATABASE alpha_smartphone;

-- Connect to the database
\c alpha_smartphone;

-- Verify database is created
\l
```

Type `\q` to exit.

## Step 4: Update Backend Configuration

Open `src/main/resources/application.properties` and update:

```properties
# Update these values with your PostgreSQL credentials
spring.datasource.url=jdbc:postgresql://localhost:5432/alpha_smartphone
spring.datasource.username=postgres
spring.datasource.password=YOUR_POSTGRES_PASSWORD_HERE
```

**Important:** Replace `YOUR_POSTGRES_PASSWORD_HERE` with the password you set during PostgreSQL installation.

## Step 5: Run Backend in IntelliJ

1. Open the project in **IntelliJ IDEA**
2. Wait for Maven to download dependencies
3. Right-click on `AlphaSmartPhoneApplication.java`
4. Select **Run 'AlphaSmartPhoneApplication.main()'**
5. Check console for success message:
   ```
   Started AlphaSmartPhoneApplication in X seconds
   ```

## Step 6: Verify Database Tables

The backend will automatically create all required tables on first run:
- `admins` - Admin users
- `phones` - Product catalog
- `inquiries` - Customer inquiries
- `reviews` - Product reviews
- `wishlist` - User favorites
- `analytics` - User activity tracking
- `audit_logs` - System audit trail

To verify, connect to PostgreSQL and run:

```sql
-- Connect to database
\c alpha_smartphone;

-- List all tables
\dt;

-- View sample data
SELECT * FROM admins;
SELECT * FROM phones;
```

## Step 7: Test Backend API

Open your browser or use curl:

```bash
# Test phones endpoint
curl http://localhost:8080/api/phones

# Should return JSON array of phones
```

## Troubleshooting

### Error: "password authentication failed for user postgres"
**Solution:** Your password in `application.properties` is incorrect. Update it with the correct password.

### Error: "database alpha_smartphone does not exist"
**Solution:** Run Step 3 again to create the database.

### Error: "Connection refused"
**Solution:** PostgreSQL service is not running. Start it:
- Windows: `net start postgresql-x64-15`
- macOS: `brew services start postgresql@15`
- Linux: `sudo systemctl start postgresql`

### Error: "Port 5432 is already in use"
**Solution:** Another PostgreSQL instance is running or another service is using port 5432.

### Error: "role postgres does not exist"
**Solution:** Create the postgres user:
```bash
sudo -u postgres createuser --superuser $USER
```

## Default Admin Credentials

Once the backend starts successfully, these credentials are created automatically:

- **Username:** `admin`
- **Password:** `Alpha@2025`
- **Email:** `alpha.smartphone.cz@gmail.com`

Use these to login to the admin panel at: `http://localhost:3000/admin`

## Frontend Connection

1. Make sure backend is running on `http://localhost:8080`
2. Frontend will automatically connect to the backend
3. No mock data - all data comes from PostgreSQL database
4. If backend is not running, frontend will show connection error

## Database Management Tools

You can use these GUI tools to manage PostgreSQL:

- **pgAdmin 4** (included with PostgreSQL installation on Windows)
- **DBeaver** (cross-platform, free)
- **TablePlus** (macOS, paid)
- **DataGrip** (JetBrains, paid)

## Production Notes

For production deployment:
1. Use strong passwords
2. Enable SSL connections
3. Configure proper firewall rules
4. Set up regular backups
5. Use environment variables for credentials
6. Consider using managed PostgreSQL (AWS RDS, Google Cloud SQL, etc.)

## Support

If you still face issues:
1. Check PostgreSQL is running
2. Verify database exists (`alpha_smartphone`)
3. Ensure credentials are correct in `application.properties`
4. Check backend console logs for detailed errors
5. Test PostgreSQL connection independently before running backend
```

I've successfully switched the backend from H2 to PostgreSQL and removed all mock data fallbacks from both the phones page and featured phones component. The frontend will now only display data from the PostgreSQL database through the Spring Boot API, ensuring strict backend-frontend integration with proper error handling and user notifications when the backend is unavailable.
