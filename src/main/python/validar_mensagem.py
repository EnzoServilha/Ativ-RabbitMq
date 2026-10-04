import json
import sys
from decimal import Decimal, InvalidOperation


REQUIRED_FIELDS = [
    "evento",
    "ordemServicoId",
    "nomeCliente",
    "modeloCarro",
    "placa",
    "descricaoProblema",
    "mecanicoResponsavel",
    "valorEstimado",
    "status",
    "criadaEm",
]

MIN_LENGTHS = {
    "nomeCliente": 3,
    "modeloCarro": 3,
    "placa": 7,
    "descricaoProblema": 10,
    "mecanicoResponsavel": 3,
}


def reject(message):
    print(message, file=sys.stderr)
    return 1


def validate_text_field(message, field, min_length):
    value = message[field]
    if not isinstance(value, str) or not value.strip():
        return f"Campo {field} esta vazio."

    if len(value.strip()) < min_length:
        return f"Campo {field} deve ter pelo menos {min_length} caracteres."

    return None


def main():
    payload = sys.stdin.read()
    if not payload or not payload.strip():
        return reject("Mensagem vazia.")

    try:
        message = json.loads(payload)
    except json.JSONDecodeError:
        return reject("Mensagem nao esta em JSON valido.")

    if not isinstance(message, dict):
        return reject("Mensagem deve ser um objeto JSON.")

    missing_fields = [
        field for field in REQUIRED_FIELDS
        if field not in message or message[field] is None
    ]
    if missing_fields:
        return reject("Campos obrigatorios ausentes: " + ", ".join(missing_fields))

    if message["evento"] != "ORDEM_SERVICO_CRIADA":
        return reject("Campo evento invalido.")

    if type(message["ordemServicoId"]) is not int or message["ordemServicoId"] <= 0:
        return reject("Campo ordemServicoId deve ser um numero positivo.")

    for field, min_length in MIN_LENGTHS.items():
        error = validate_text_field(message, field, min_length)
        if error:
            return reject(error)

    try:
        valor_estimado = Decimal(str(message["valorEstimado"]))
    except (InvalidOperation, ValueError):
        return reject("Campo valorEstimado deve ser um numero.")

    if valor_estimado < 0:
        return reject("Campo valorEstimado nao pode ser negativo.")

    if not str(message["status"]).strip():
        return reject("Campo status esta vazio.")

    if not str(message["criadaEm"]).strip():
        return reject("Campo criadaEm esta vazio.")

    print("Mensagem valida.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
