const API_BASE_URL = 'http://127.0.0.1:8080/api';

export async function get<T>(endpoint: string): Promise<T> {
    const res = await fetch(API_BASE_URL + "/" + endpoint);
    const json = await res.json();
    return json;
};

export async function post<T>(endpoint: string, data: T): Promise<T> {
    const res = await fetch(API_BASE_URL + "/" + endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    const json = await res.json();
    return json;
}

export async function put<T>(endpoint: string, data: T): Promise<T> {
    const res = await fetch(API_BASE_URL + "/" + endpoint, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
    const json = await res.json();
    return json;
}

export async function del<T>(endpoint: string): Promise<T> {
    const res = await fetch(API_BASE_URL + "/" + endpoint, { method: 'DELETE' });
    const json = await res.json();
    return json;
};