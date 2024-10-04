output=$(curl --location '127.0.0.1:8001/api/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "Eve",
    "role": ["USER"],
    "email": "eve@gmail.com",
    "password": "password"
}')
echo "$output"


output=$(curl --location '127.0.0.1:8001/api/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "Alice",
    "role": ["USER", "ADMIN"],
    "email": "alice@gmail.com",
    "password": "password"
}')
echo "$output"

output=$(curl --location '127.0.0.1:8001/api/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "Bob",
    "role": ["USER"],
    "email": "bob@gmail.com",
    "password": "password"
}')
echo "$output"

output=$(curl --location '127.0.0.1:8001/api/auth/signin' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "Jane",
    "role": ["USER"],
    "email": "jane@gmail.com",
    "password": "password"
}')
echo "$output"

rc=$?
if [ $rc == 0 ] ; then
  echo "Mock users successfully signed!"
else
  echo "Failed while registering users. exit code: $rc"
  exit "$rc"
fi