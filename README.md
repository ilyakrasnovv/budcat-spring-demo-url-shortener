### Required env vairables

- Environmental variables are being loaded at [NotOnlyEnv.java](/src/main/java/ru/ilkras/budcat/data/NotOnlyEnv.java)
  MAX_URL_LENGTH=256;DATABASE_PATH=jdbc:h2:./budcat_database
    - `BASE_URL` (String, url/ip, i.e. `BASE_URL=0.0.0.0:8080;`, `BASE_URL=ilkras.ru;`)
    - `DEBUG_ENDPOINTS` (Boolean, true/false, not 1/0, i.e. `DEBUG_ENDPOINTS=true;`)
    - `DATABASE_PATH` h2 database path (i.e. `DATABASE_PATH=jdbc:h2:./budcat_database;`)
- All in all, it can look like this: ``BASE_URL=0.0.0.0:8080;DEBUG_ENDPOINTS=true;DATABASE_PATH=jdbc:h2:./budcat_database``

### Endpoints

- Endpoints handling is located at [MainRouting.java](src/main/java/ru/ilkras/budcat/api/MainRouting.java)
    - The following two endpoints return json with info about the original url and the shortened one (
      i.e. `{"origin":"http://yandex.ru","shortened":"0.0.0.0:8080/u/0"}`). You can test them using forms located at base url.
      - GET `url/shorten` accepts only parameter `longUrl`.
      It [adds](src/main/java/ru/ilkras/budcat/utilities/URLFormatter.java) "http://", if your url lacks one :)
      - GET `url/expand` accepts only parameter `id` - id of the shortened link (that number after `/u/`)
    - GET `/u/$id`, where id is the id of the shortened link. Returns 301 MOVED_PERMANENTLY and redirects to the expanded link 
    
