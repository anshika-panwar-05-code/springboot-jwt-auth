document.getElementById('prForm').addEventListener('submit', async (event) => {
    event.preventDefault();
  
    const repoOwner = document.getElementById('repoOwner').value;
    const repoName = document.getElementById('repoName').value;
    const prNumber = document.getElementById('prNumber').value;
  
    const responseDiv = document.getElementById('response');
  
    try {
        const response = await fetch('http://localhost:8080/api/prscan', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token') // if your spring auth uses JWT
            },
            body: JSON.stringify({
                repoOwner,
                repoName,
                prNumber,
            }),
        });


        if (!response.ok) {
        throw new Error('Failed to fetch scan results');
      }
  
      const data = await response.json();
      responseDiv.innerHTML = `<pre>${JSON.stringify(data, null, 2)}</pre>`;
    } catch (error) {
      responseDiv.innerHTML = `<p style="color: red;">Error: ${error.message}</p>`;
    }
  });
  