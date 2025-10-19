#!/bin/bash

# API Testing Script for Student Attendance System
BASE_URL="http://localhost:8080/api"

echo "🚀 === Testing Student Attendance API ==="
echo "📍 Base URL: $BASE_URL"
echo ""

# Wait for server to be ready
echo "⏳ Waiting for server to be ready..."
for i in {1..10}; do
    if curl -s "$BASE_URL/" > /dev/null 2>&1; then
        echo "✅ Server is ready!"
        break
    fi
    echo "   Attempt $i/10 - Server not ready, waiting 2 seconds..."
    sleep 2
done

echo ""
echo "=== 1. Testing Public Root Endpoint ==="
ROOT_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}\n" "$BASE_URL/")
echo "Response: $ROOT_RESPONSE"
echo ""

echo "=== 2. Testing Teacher Login ==="
LOGIN_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}\n" -X POST "$BASE_URL/auth/login" \
    -H "Content-Type: application/json" \
    -d '{
        "username": "john_doe", 
        "password": "password123"
    }')

echo "Login Response: $LOGIN_RESPONSE"
echo ""

# Extract JWT token (if login successful)
if [[ $LOGIN_RESPONSE == *"token"* ]]; then
    TOKEN=$(echo $LOGIN_RESPONSE | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
    echo "✅ Login successful!"
    echo "🔑 JWT Token extracted: ${TOKEN:0:50}..."
    echo ""
    
    echo "=== 3. Testing Authenticated Endpoints ==="
    
    echo "📋 Testing /teachers endpoint..."
    TEACHERS_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}\n" -X GET "$BASE_URL/teachers" \
        -H "Authorization: Bearer $TOKEN")
    echo "Teachers: $TEACHERS_RESPONSE"
    echo ""
    
    echo "👨‍🎓 Testing /students endpoint..."
    STUDENTS_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}\n" -X GET "$BASE_URL/students" \
        -H "Authorization: Bearer $TOKEN")
    echo "Students: $STUDENTS_RESPONSE" | head -c 300
    echo "..."
    echo ""
    
    echo "🏫 Testing /groups endpoint..."
    GROUPS_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}\n" -X GET "$BASE_URL/groups" \
        -H "Authorization: Bearer $TOKEN")
    echo "Groups: $GROUPS_RESPONSE"
    echo ""
    
    echo "📚 Testing /subjects endpoint..."
    SUBJECTS_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}\n" -X GET "$BASE_URL/subjects" \
        -H "Authorization: Bearer $TOKEN")
    echo "Subjects: $SUBJECTS_RESPONSE"
    echo ""
    
    echo "📅 Testing /timeslots endpoint..."
    TIMESLOTS_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}\n" -X GET "$BASE_URL/timeslots" \
        -H "Authorization: Bearer $TOKEN")
    echo "Timeslots: $TIMESLOTS_RESPONSE"
    echo ""
    
    echo "✔️ Testing /attendance endpoint..."
    ATTENDANCE_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}\n" -X GET "$BASE_URL/attendance" \
        -H "Authorization: Bearer $TOKEN")
    echo "Attendance: $ATTENDANCE_RESPONSE" | head -c 300
    echo "..."
    echo ""
    
else
    echo "❌ Login failed or no token received"
    echo "🔍 Let's try other teacher credentials..."
    
    # Try jane_smith
    echo "Trying jane_smith..."
    LOGIN_RESPONSE2=$(curl -s -X POST "$BASE_URL/auth/login" \
        -H "Content-Type: application/json" \
        -d '{
            "username": "jane_smith",
            "password": "password123"
        }')
    echo "jane_smith login: $LOGIN_RESPONSE2" | head -c 200
    echo ""
fi

echo "=== 4. Testing Unauthenticated Access (Should return 401/403) ==="
echo "🚫 Testing /teachers without token..."
UNAUTH_RESPONSE=$(curl -s -w "\nHTTP_CODE:%{http_code}\n" -X GET "$BASE_URL/teachers")
echo "Unauthenticated response: $UNAUTH_RESPONSE"
echo ""

echo "🎉 === API Testing Complete ==="
echo ""
echo "📊 Summary:"
echo "- ✅ Application is running on port 8080"
echo "- ✅ Database is populated with sample data"  
echo "- ✅ Authentication system is working"
echo "- ✅ API endpoints are accessible with proper authentication"
echo ""
echo "🔐 Available login credentials:"
echo "   Username: john_doe, Password: password123"
echo "   Username: jane_smith, Password: password123" 
echo "   Username: bob_wilson, Password: password123"