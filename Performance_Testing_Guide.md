 Performance Test (JMeter)

---

Create the Test Plan

Set Up Thread Group (50 Users)

In JMeter:
1. Right-click **Test Plan** → Add → Threads (Users) → **Thread Group**
2. Set these values:

| Setting | Value | Meaning |
|---------|-------|---------|
| Number of Threads | `50` | 50 concurrent (virtual) users |
| Ramp-up Period | `60` | Start all 50 users over 60 seconds |
| Loop Count | `2` | Each user runs the test 2 times |
| Duration (seconds) | `120` | Run for 2 minutes total |

> Check **"Scheduler"** and set Duration = 120 to run for exactly 2 minutes.

### 2.2 — Add HTTP Header Manager (Authentication)

1. Right-click Thread Group → Add → Config Element → **HTTP Header Manager**
2. Add these headers:

| Name | Value |
|------|-------|
| Content-Type | application/json |
| Accept | application/json |
| Authorization | Bearer YOUR_TOKEN_HERE |

> **How to get your token**: Run the Postman Login request (from API_Testing_Guide.md) and copy the token from the response.

### 2.3 — Add HTTP Request (Add Employee API)

1. Right-click Thread Group → Add → Sampler → **HTTP Request**
2. Fill in:

| Field | Value |
|-------|-------|
| Protocol | https |
| Server Name | opensource-demo.orangehrmlive.com |
| Method | POST |
| Path | /web/index.php/api/v2/pim/employees |

3. In the **Body Data** tab, paste:
```json
{
    "firstName": "Perf${__threadNum}",
    "middleName": "",
    "lastName": "Test${__Random(1000,9999)}"
}
```
> `${__threadNum}` makes each user's name unique (User1, User2, etc.)
> `${__Random(1000,9999)}` adds a random number so names don't repeat

### 2.4 — Add Listeners (to see results)

Add these three listeners (right-click Thread Group → Add → Listener):

1. **View Results Tree** — See each request pass/fail in detail
2. **Summary Report** — See average response time, throughput
3. **Aggregate Report** — See min/max/90th percentile response times

---

## Step 3: Run the Test

1. Click the **green Play button** (▶) at the top
2. Watch results in the listeners
3. Let it run for 2 minutes
4. Click the **red Stop button** when done

---

## Step 4: Understand the Results

### Summary Report columns explained:

| Column | What it means | Good target |
|--------|--------------|-------------|
| # Samples | Total requests sent | - |
| Average | Average response time (ms) | < 2000ms |
| Min | Fastest single response | - |
| Max | Slowest single response | - |
| 90% Line | 90% of requests were faster than this | < 3000ms |
| Error % | % of failed requests | < 1% |
| Throughput | Requests per second the server handled | Higher is better |

---


### Test Configuration
- Tool: Apache JMeter 5.x
- Test type: Load Test
- Concurrent Users: 50
- Ramp-up Period: 60 seconds
- Test Duration: 120 seconds (2 minutes)
- API Under Test: POST /api/v2/pim/employees

### Results Table
(Fill in from your JMeter Summary Report)

| Metric | Value |
|--------|-------|
| Total Requests | |
| Average Response Time | ms |
| Minimum Response Time | ms |
| Maximum Response Time | ms |
| 90th Percentile | ms |
| Error Rate | % |
| Throughput | req/sec |

### Observations
Example observations to include:
- "The system handled 50 concurrent users with an average response time of Xms"
- "Error rate was below 1%, indicating stable performance"
- "Response time increased during the first 30 seconds (ramp-up phase)"
- "Maximum response time was Xms, which is acceptable for a write operation"

### Conclusion
- Was the performance acceptable? (under 3 seconds average = good)
- Were there any errors? (should be < 1%)
- Recommendation: pass / needs improvement

---

## Quick JMeter Command Line Run (No GUI needed for CI):

```bash
# Run test (replace with your .jmx file path)
jmeter -n -t orangehrm_performance.jmx -l results.jtl -e -o report_output/

# -n = non-GUI mode
# -t = test plan file
# -l = log file
# -e = generate HTML report
# -o = output folder for HTML report
```

Open `report_output/index.html` in browser to see a full HTML report.
