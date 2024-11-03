import sys
import pandas as pd
import matplotlib.pyplot as plt

def generate_graphs(excel_file, output_folder):
    # Charger les donnees Excel
    data = pd.read_excel(excel_file)

    # Courbe de "Valeur_A" dans le temps
    plt.figure(figsize=(10, 6))
    plt.plot(data['Date'], data['Valeur_A'], label='Valeur_A')
    plt.xlabel("Date")
    plt.ylabel("Valeur_A")
    plt.title("Courbe de Valeur_A dans le temps")
    plt.legend()
    plt.savefig(f"{output_folder}/courbe_valeur_a.png")
    plt.close()

    # Histogramme de "Valeur_A"
    plt.figure(figsize=(8, 6))
    plt.hist(data['Valeur_A'], bins=15, alpha=0.7, color='blue')
    plt.xlabel("Valeur_A")
    plt.ylabel("Frequence")
    plt.title("Histogramme de Valeur_A")
    plt.savefig(f"{output_folder}/histogramme_valeur_a.png")
    plt.close()

    # Histogramme de "Valeur_B"
    plt.figure(figsize=(8, 6))
    plt.hist(data['Valeur_B'], bins=15, alpha=0.7, color='green')
    plt.xlabel("Valeur_B")
    plt.ylabel("Frequence")
    plt.title("Histogramme de Valeur_B")
    plt.savefig(f"{output_folder}/histogramme_valeur_b.png")
    plt.close()

    print("Graphiques generes et enregistres avec succes")

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print("Usage: python generate_graphs.py <excel_file> <output_folder>")
        sys.exit(1)

    excel_file = sys.argv[1]
    output_folder = sys.argv[2]

    generate_graphs(excel_file, output_folder)
