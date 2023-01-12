/** @type {import('tailwindcss').Config} */
module.exports = {
    mode: process.env.NODE_ENV ? 'jit' : undefined,
    purge: ["./src/main/resources/**/*.html", "./src/**/*.js"],
    darkMode: false,
    content: ["./src/**/*.{html,js}"],
    theme: {
        extend: {
            width: {
                '800': '800px',
                '500': '500px',
            }
        },
    },
    plugins: [],
}
