const API_BASE_URL = 'http://localhost:8080/api/projects';

// Функция для создания нового проекта
document.getElementById('createProjectForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const projectData = {
        name: document.getElementById('name').value,
        code: document.getElementById('code').value,
        startDate: document.getElementById('startDate').value,
        endDate: document.getElementById('endDate').value,
        status: document.getElementById('status').value
    };

    fetch(API_BASE_URL, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(projectData)
    })
        .then(response => response.json())
        .then(data => {
            alert('Project created successfully!');
            console.log(data);
            getProjects(); // Обновить список
        })
        .catch(error => console.error('Error:', error));
});

// Функция для получения всех проектов
function getProjects() {
    fetch(API_BASE_URL)
        .then(response => response.json())
        .then(data => {
            const projectsList = document.getElementById('projectsList');
            projectsList.innerHTML = '';

            data.forEach(project => {
                const listItem = document.createElement('li');
                listItem.textContent = `${project.name} (${project.code}) - ${project.status}`;
                projectsList.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error:', error));
}
