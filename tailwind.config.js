/** @type {import('tailwindcss').Config} */
module.exports = {
    content: [
        './menu/*.{html,js}'
    ],
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
