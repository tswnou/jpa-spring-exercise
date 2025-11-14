const empForm = document.getElementById('employeeForm');
const assignForm = document.getElementById('assignForm');

//Load data on page load
window.addEventListener('DOMContentLoaded', () => {
  loadEmployees();
  loadProjects();
});


// ======================= EMPLOYEES =======================

empForm.addEventListener('submit', async e => {
  e.preventDefault();
  await createEmployee();
});

async function createEmployee() {
  const name = document.getElementById('empName').value;
  const age = document.getElementById('empAge').value;
  const role = document.getElementById('empRole').value;

  if (!name || !age || !role) {
    alert('Please fill all employee fields');
    return;
  }

  const employee = {
    name,
    age: parseInt(age),
    role
  };

  const res = await fetch('/api/employees', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(employee)
  });

  if (res.ok) {
    alert('Employee added!');
    empForm.reset();
    loadEmployees();
  } else {
    alert('Error adding employee');
  }
}

async function loadEmployees() {
  const res = await fetch('/api/employees');
  if (!res.ok) {
    console.error('Failed to load employees');
    return;
  }

  const employees = await res.json();
  console.log('Employees from backend:', employees);

  const tbody = document.querySelector('#employeeTable tbody');
  tbody.innerHTML = '';

  employees.forEach(e => {
    tbody.innerHTML += `
      <tr>
        <td>${e.id}</td>
        <td>${e.name}</td>
        <td>${e.age}</td>
        <td>${e.role}</td>
      </tr>`;
  });
}


// ======================= PROJECTS =======================

// Project form uses inline onclick="createProject()" in your HTML
async function createProject() {
  const name = document.getElementById('projectName').value;
  const startDate = document.getElementById('projectStart').value;
  const endDate = document.getElementById('projectEnd').value;
  const budget = document.getElementById('projectBudget').value;

  if (!name || !startDate || !endDate || !budget) {
    alert('Please fill all project fields');
    return;
  }

  const project = {
    name,
    startDate,
    endDate,
    budget: parseFloat(budget)
  };

  const res = await fetch('/api/projects', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(project)
  });

  if (res.ok) {
    alert('Project added!');
    document.getElementById('projectName').value = '';
    document.getElementById('projectStart').value = '';
    document.getElementById('projectEnd').value = '';
    document.getElementById('projectBudget').value = '';
    loadProjects();
  } else {
    alert('Failed to add project');
  }
}

async function loadProjects() {
  const res = await fetch('/api/projects');
  if (!res.ok) {
    console.error('Failed to load projects');
    return;
  }

  const projects = await res.json();
  console.log('Projects from backend:', projects);

  const tbody = document.querySelector('#projectTable tbody');
  tbody.innerHTML = '';

  projects.forEach(p => {
    // main project row
    const row = document.createElement('tr');
    row.innerHTML = `
      <td>${p.id}</td>
      <td>${p.name}</td>
      <td>${p.startDate || ''}</td>
      <td>${p.endDate || ''}</td>
      <td>${p.budget || ''}</td>
      <td>
        <button class="view-btn" data-id="${p.id}">View Employees</button>
      </td>
    `;
    tbody.appendChild(row);

    // row that will hold employee list
    const employeeRow = document.createElement('tr');
    employeeRow.innerHTML = `
      <td colspan="6">
        <div class="employee-list" id="project-${p.id}-employees"></div>
      </td>
    `;
    tbody.appendChild(employeeRow);
  });

  // add click listeners AFTER building table
  document.querySelectorAll('.view-btn').forEach(btn => {
    btn.addEventListener('click', () => toggleEmployeeList(btn.dataset.id));
  });
}


// ======================= ASSIGN EMPLOYEE =======================

assignForm.addEventListener('submit', async e => {
  e.preventDefault();
  const empId = assignForm.assignEmpId.value;
  const projId = assignForm.assignProjId.value;

  const res = await fetch(`/api/projects/${projId}/assign/${empId}`, {
    method: 'POST'
  });

  if (res.ok) {
    alert('Employee assigned!');
    loadProjects(); // refresh project list
  } else {
    alert('Assignment failed.');
  }
});


// ======================= VIEW EMPLOYEES PER PROJECT =======================

async function toggleEmployeeList(projectId) {
  const listDiv = document.getElementById(`project-${projectId}-employees`);

  // toggle off
  if (listDiv.classList.contains('show')) {
    listDiv.classList.remove('show');
    listDiv.innerHTML = '';
    return;
  }

  // fetch one project with employees
  const res = await fetch(`/api/projects/${projectId}`);
  if (!res.ok) {
    listDiv.innerHTML = '<p>Could not load employee data.</p>';
    return;
  }
  const project = await res.json();
  console.log(`Project ${projectId} details:`, project);

  const employees = project.employees;

  if (!employees || employees.length === 0) {
    listDiv.innerHTML = '<p>No employees assigned to this project.</p>';
  } else {
    listDiv.innerHTML = employees
      .map(e => `<div class="employee-item">${e.name} (${e.role})</div>`)
      .join('');
  }

  listDiv.classList.add('show');
}

// =============== ADD COUNTRY ===============
document.getElementById("countryForm").addEventListener("submit", async e => {
    e.preventDefault();

    const country = {
        name: document.getElementById("countryName").value,
        description: document.getElementById("countryDesc").value
    };

    const res = await fetch('/api/countries', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(country)
    });

    if (res.ok) {
        alert("Country added!");
        e.target.reset();
    } else {
        alert("Error adding country");
    }
});

// =============== ADD ADDRESS ===============
document.getElementById("addressForm").addEventListener("submit", async e => {
    e.preventDefault();

    const address = {
        streetName: document.getElementById("addrStreetName").value,
        streetNumber: document.getElementById("addrStreetNumber").value,
        zipCode: document.getElementById("addrZip").value,
        city: document.getElementById("addrCity").value,
        country: { name: document.getElementById("addrCountryCode").value }
    };

    const res = await fetch('/api/addresses', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(address)
    });

    if (res.ok) {
        alert("Address added!");
        e.target.reset();
    } else {
        alert("Error adding address");
    }
});

// =============== ADD DEPARTMENT ===============
document.getElementById("departmentForm").addEventListener("submit", async e => {
    e.preventDefault();

    const dept = {
        name: document.getElementById("deptName").value,
        addressId: parseInt(document.getElementById("deptAddressId").value)
    };

    const res = await fetch('/api/departments', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dept)
    });

    if (res.ok) {
        alert("Department added!");
        e.target.reset();
    } else {
        alert("Error adding department");
    }
});

// =============== ADD PHONE ===============
document.getElementById("phoneForm").addEventListener("submit", async e => {
    e.preventDefault();

    const phone = {
        value: document.getElementById("phoneValue").value,
        employeeId: parseInt(document.getElementById("phoneEmployeeId").value)
    };

    const res = await fetch('/api/phones', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(phone)
    });

    if (res.ok) {
        alert("Phone added!");
        e.target.reset();
    } else {
        alert("Error adding phone");
    }
});




document.addEventListener('DOMContentLoaded', () => {
    const toggleBtn = document.getElementById("toggleExtraBtn");
    const extraSection = document.getElementById("extra-actions");

    toggleBtn.addEventListener("click", () => {
        const isHidden = extraSection.classList.toggle("hidden");

        // Update button label
        toggleBtn.textContent = isHidden
            ? "More options ▸"
            : "Hide options ▾";
    });
});
